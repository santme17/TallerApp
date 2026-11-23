package com.example.inventarioapp.feature.book.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventarioapp.feature.book.domain.repository.BookRepository
import com.example.inventarioapp.feature.book.presentation.state.BookListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(BookListUiState())
    val ui: StateFlow<BookListUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeBooks()
                .catch { e -> _ui.update { it.copy(message = e.message) } }
                .collect { books ->
                    _ui.update { it.copy(books = books) }
                }
        }
        sync()
    }

    fun sync() {
        viewModelScope.launch {
            _ui.update { it.copy(isSyncing = true, message = null) }
            try {
                val report = repository.syncNow()
                _ui.update {
                    it.copy(
                        message = "Sincronizado: ${report.uploaded} enviados, " +
                            "${report.downloaded} en la nube"
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _ui.update { it.copy(message = "Sin conexion: datos locales disponibles") }
            } finally {
                _ui.update { it.copy(isSyncing = false) }
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            repository.delete(id)
            sync()
        }
    }
}
