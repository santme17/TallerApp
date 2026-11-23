package com.example.inventarioapp.feature.book.data.repository

import com.example.inventarioapp.feature.book.data.local.BookDao
import com.example.inventarioapp.feature.book.data.mapper.toDomain
import com.example.inventarioapp.feature.book.data.mapper.toDto
import com.example.inventarioapp.feature.book.data.mapper.toEntity
import com.example.inventarioapp.feature.book.data.remote.FirestoreBookDataSource
import com.example.inventarioapp.feature.book.domain.model.Book
import com.example.inventarioapp.feature.book.domain.repository.BookRepository
import com.example.inventarioapp.feature.book.domain.repository.SyncReport
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val dao: BookDao,
    private val remote: FirestoreBookDataSource,
    private val auth: FirebaseAuth?
) : BookRepository {

    private val syncMutex = Mutex()

    override fun observeBooks(): Flow<List<Book>> =
        dao.observeVisible().map { rows -> rows.map { it.toDomain() } }

    override suspend fun findById(id: String): Book? =
        dao.find(id)?.takeUnless { it.isDeleted }?.toDomain()

    override suspend fun save(book: Book) {
        dao.upsert(
            book.copy(updatedAt = System.currentTimeMillis()).toEntity()
        )
    }

    override suspend fun delete(id: String) {
        val current = dao.find(id) ?: return
        dao.upsert(
            current.copy(
                isDeleted = true,
                pending = true,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun syncNow(): SyncReport = syncMutex.withLock {
        val firebaseAuth = auth ?: error("Firebase no inicializado")
        val uid = firebaseAuth.currentUser?.uid
            ?: firebaseAuth.signInAnonymously().await().user?.uid
            ?: error("No fue posible iniciar sesion anonima")

        var uploaded = 0
        for (local in dao.getPending()) {
            remote.push(uid, local.toDto())
            dao.markSynced(local.id, local.updatedAt)
            uploaded++
        }

        val cloudItems = remote.fetchFromServer(uid)
        for (dto in cloudItems) {
            val cloud = dto.toEntity()
            val local = dao.find(cloud.id)
            if (local?.pending == true) continue
            if (local == null || cloud.updatedAt > local.updatedAt) {
                dao.upsert(cloud)
            }
        }
        SyncReport(uploaded, cloudItems.size)
    }
}
