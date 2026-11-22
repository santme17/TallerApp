package com.example.inventarioapp.feature.book.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreBookDataSource @Inject constructor(
    private val db: FirebaseFirestore?
) {
    suspend fun push(uid: String, dto: BookDto) {
        val firestoreDb = db ?: error("Firebase no inicializado")
        val data = mapOf(
            "id" to dto.id,
            "title" to dto.title,
            "author" to dto.author,
            "pages" to dto.pages,
            "isRead" to dto.isRead,
            "updatedAt" to dto.updatedAt,
            "isDeleted" to dto.isDeleted
        )
        firestoreDb.collection("users").document(uid).collection("books").document(dto.id).set(data).await()
    }

    suspend fun fetchFromServer(uid: String): List<BookDto> {
        val firestoreDb = db ?: error("Firebase no inicializado")
        return firestoreDb.collection("users").document(uid).collection("books").get(Source.SERVER).await().documents.mapNotNull { doc ->
            val id = doc.getString("id") ?: return@mapNotNull null
            BookDto(
                id = id,
                title = doc.getString("title").orEmpty(),
                author = doc.getString("author").orEmpty(),
                pages = (doc.getLong("pages") ?: 0L).toInt(),
                isRead = doc.getBoolean("isRead") ?: false,
                updatedAt = doc.getLong("updatedAt") ?: 0L,
                isDeleted = doc.getBoolean("isDeleted") ?: false
            )
        }
    }
}
