package com.example.inventarioapp.feature.book.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioapp.feature.book.domain.model.Book

@Composable
fun BookCard(
    book: Book,
    onEdit: () -> Unit,
    onAskDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text("Autor: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("Páginas: ${book.pages} | ${if (book.isRead) "Leído" else "Pendiente de leer"}")
            Row {
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(onClick = onAskDelete) { Text("Eliminar") }
            }
        }
    }
}
