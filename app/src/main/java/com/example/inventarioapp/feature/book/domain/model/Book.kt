package com.example.inventarioapp.feature.book.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val pages: Int,
    val isRead: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
