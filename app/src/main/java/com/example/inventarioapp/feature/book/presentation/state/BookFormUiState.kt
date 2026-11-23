package com.example.inventarioapp.feature.book.presentation.state

data class BookFormUiState(
    val title: String = "",
    val author: String = "",
    val pages: String = "",
    val isRead: Boolean = false,
    val saving: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)
