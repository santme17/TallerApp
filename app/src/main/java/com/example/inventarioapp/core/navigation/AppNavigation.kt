package com.example.inventarioapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.inventarioapp.feature.book.presentation.screen.BookFormScreen
import com.example.inventarioapp.feature.book.presentation.screen.BookListScreen
import com.example.inventarioapp.feature.book.presentation.viewmodel.BookFormViewModel
import com.example.inventarioapp.feature.book.presentation.viewmodel.BookListViewModel
import kotlinx.serialization.Serializable

@Serializable
data object BookListRoute : NavKey

@Serializable
data class BookFormRoute(val id: String? = null) : NavKey

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(BookListRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<BookListRoute> {
                val vm: BookListViewModel = hiltViewModel()
                BookListScreen(
                    vm = vm,
                    onCreate = { backStack.add(BookFormRoute()) },
                    onEdit = { id -> backStack.add(BookFormRoute(id)) }
                )
            }
            entry<BookFormRoute> { route ->
                val vm: BookFormViewModel = hiltViewModel()
                BookFormScreen(
                    vm = vm,
                    bookId = route.id,
                    onDone = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
