package com.example.inventarioapp.feature.book.data.mapper

import com.example.inventarioapp.feature.book.data.local.BookEntity
import com.example.inventarioapp.feature.book.domain.model.Book

fun BookEntity.toDomain() = Book(
    id, title, author, pages, isRead, updatedAt
)

fun Book.toEntity() = BookEntity(
    id = id, title = title, author = author,
    pages = pages, isRead = isRead,
    updatedAt = updatedAt, pending = true, isDeleted = false
)
