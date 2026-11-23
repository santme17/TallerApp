package com.example.inventarioapp.feature.book.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioapp.feature.book.domain.model.Book
import com.example.inventarioapp.feature.book.presentation.composables.BookCard

@Composable
fun BookListSection(
    books: List<Book>,
    onEdit: (String) -> Unit,
    onAskDelete: (String) -> Unit
) {
    if (books.isEmpty()) {
        Text("Sin libros. Pulsa + para comenzar.", Modifier.padding(24.dp))
        return
    }
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books, key = { it.id }) { book ->
            BookCard(
                book = book,
                onEdit = { onEdit(book.id) },
                onAskDelete = { onAskDelete(book.id) }
            )
        }
    }
}
