package com.example.inventarioapp.feature.book.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query(
        "SELECT * FROM books WHERE isDeleted = 0 " +
            "ORDER BY updatedAt DESC"
    )
    fun observeVisible(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun find(id: String): BookEntity?

    @Query("SELECT * FROM books WHERE pending = 1")
    suspend fun getPending(): List<BookEntity>

    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query(
        "UPDATE books SET pending = 0 " +
            "WHERE id = :id AND updatedAt = :version"
    )
    suspend fun markSynced(id: String, version: Long)
}
