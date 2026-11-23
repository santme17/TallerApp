package com.example.inventarioapp.feature.book.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventarioapp.feature.book.domain.model.Book
import com.example.inventarioapp.feature.book.domain.repository.BookRepository
import com.example.inventarioapp.feature.book.domain.usecase.SaveBookUseCase
import com.example.inventarioapp.feature.book.presentation.state.BookFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookFormViewModel @Inject constructor(
    private val repository: BookRepository,
    private val saveBook: SaveBookUseCase
) : ViewModel() {

    private val _ui = MutableStateFlow(BookFormUiState())
    val ui: StateFlow<BookFormUiState> = _ui.asStateFlow()

    private var editingId: String? = null
    private var loaded = false

    fun load(id: String?) {
        if (loaded) return
        loaded = true
        editingId = id
        if (id == null) return
        viewModelScope.launch {
            val book = repository.findById(id) ?: return@launch
            _ui.value = BookFormUiState(
                title = book.title,
                author = book.author,
                pages = book.pages.toString(),
                isRead = book.isRead
            )
        }
    }

    fun updateTitle(v: String) = _ui.update { it.copy(title = v) }
    fun updateAuthor(v: String) = _ui.update { it.copy(author = v) }
    fun updatePages(v: String) = _ui.update { it.copy(pages = v) }
    fun updateIsRead(v: Boolean) = _ui.update { it.copy(isRead = v) }

    fun save() {
        viewModelScope.launch {
            val current = _ui.value
            val pages = current.pages.trim().toIntOrNull()
            if (pages == null) {
                _ui.value = current.copy(error = "El número de páginas debe ser numérico")
                return@launch
            }
            _ui.value = current.copy(saving = true, error = null)
            try {
                saveBook(
                    Book(
                        id = editingId ?: UUID.randomUUID().toString(),
                        title = current.title.trim(),
                        author = current.author.trim(),
                        pages = pages,
                        isRead = current.isRead
                    )
                )
                _ui.update { it.copy(saved = true, saving = false) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _ui.update {
                    it.copy(saving = false, error = e.message ?: "Error de guardado")
                }
            }
        }
    }
}
