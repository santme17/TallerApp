package com.example.inventarioapp.feature.book.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventarioapp.feature.book.presentation.section.BookFormSection
import com.example.inventarioapp.feature.book.presentation.viewmodel.BookFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookFormScreen(
    vm: BookFormViewModel,
    bookId: String?,
    onDone: () -> Unit
) {
    val ui by vm.ui.collectAsStateWithLifecycle()

    LaunchedEffect(bookId) { vm.load(bookId) }
    LaunchedEffect(ui.saved) { if (ui.saved) onDone() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (bookId == null) "Nuevo libro" else "Editar libro")
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            BookFormSection(
                ui = ui,
                onTitle = vm::updateTitle,
                onAuthor = vm::updateAuthor,
                onPages = vm::updatePages,
                onIsRead = vm::updateIsRead,
                onSave = vm::save
            )
        }
    }
}
