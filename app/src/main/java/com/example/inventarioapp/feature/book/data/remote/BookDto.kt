package com.example.inventarioapp.feature.book.data.remote

data class BookDto(
    val id: String,
    val title: String,
    val author: String,
    val pages: Int,
    val isRead: Boolean,
    val updatedAt: Long,
    val isDeleted: Boolean
)
