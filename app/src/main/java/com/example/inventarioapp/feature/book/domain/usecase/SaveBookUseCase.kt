package com.example.inventarioapp.feature.book.domain.usecase

import com.example.inventarioapp.feature.book.domain.model.Book
import com.example.inventarioapp.feature.book.domain.repository.BookRepository
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book) {
        require(book.title.isNotBlank()) { "Título obligatorio" }
        require(book.title.length <= 100) { "Título: máximo 100 caracteres" }
        require(book.author.isNotBlank()) { "Autor obligatorio" }
        require(book.author.length <= 80) { "Autor: máximo 80 caracteres" }
        require(book.pages > 0) { "El número de páginas debe ser mayor a 0" }
        repository.save(book)
    }
}
