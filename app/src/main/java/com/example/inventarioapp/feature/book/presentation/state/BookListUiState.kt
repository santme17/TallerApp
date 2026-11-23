package com.example.inventarioapp.feature.book.presentation.state

import com.example.inventarioapp.feature.book.domain.model.Book

data class BookListUiState(
    val books: List<Book> = emptyList(),
    val isSyncing: Boolean = false,
    val message: String? = null
)
