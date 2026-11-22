package com.example.inventarioapp.feature.book.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val pages: Int,
    val isRead: Boolean,
    val updatedAt: Long,
    val pending: Boolean = true,
    val isDeleted: Boolean = false
)
