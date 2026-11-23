package com.example.inventarioapp.feature.book.data.mapper

import com.example.inventarioapp.feature.book.data.local.BookEntity
import com.example.inventarioapp.feature.book.data.remote.BookDto

fun BookEntity.toDto() = BookDto(
    id, title, author, pages, isRead, updatedAt, isDeleted
)

fun BookDto.toEntity() = BookEntity(
    id, title, author, pages, isRead, updatedAt,
    pending = false, isDeleted = isDeleted
)
