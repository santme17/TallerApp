package com.example.inventarioapp.feature.book.domain.repository

import com.example.inventarioapp.feature.book.domain.model.Book
import kotlinx.coroutines.flow.Flow

data class SyncReport(val uploaded: Int, val downloaded: Int)

interface BookRepository {
    fun observeBooks(): Flow<List<Book>>
    suspend fun findById(id: String): Book?
    suspend fun save(book: Book)
    suspend fun delete(id: String)
    suspend fun syncNow(): SyncReport
}
