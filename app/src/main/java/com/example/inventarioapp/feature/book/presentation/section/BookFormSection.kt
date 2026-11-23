package com.example.inventarioapp.feature.book.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inventarioapp.feature.book.presentation.composables.BookFormField
import com.example.inventarioapp.feature.book.presentation.state.BookFormUiState

@Composable
fun BookFormSection(
    ui: BookFormUiState,
    onTitle: (String) -> Unit,
    onAuthor: (String) -> Unit,
    onPages: (String) -> Unit,
    onIsRead: (Boolean) -> Unit,
    onSave: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BookFormField("Título", ui.title, onTitle)
        BookFormField("Autor", ui.author, onAuthor)
        BookFormField("Número de páginas", ui.pages, onPages, KeyboardType.Number)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = ui.isRead,
                onCheckedChange = onIsRead
            )
            Text("¿Libro leído?", modifier = Modifier.padding(start = 8.dp))
        }

        ui.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = onSave,
            enabled = !ui.saving,
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (ui.saving) "Guardando..." else "Guardar") }
    }
}
