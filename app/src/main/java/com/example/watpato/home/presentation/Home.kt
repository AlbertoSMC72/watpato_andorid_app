package com.example.watpato.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.watpato.home.data.model.Book
import androidx.compose.runtime.getValue

@Composable
fun Home(
    viewModel: HomeViewModel,
    onBookSelected: (Int) -> Unit
) {
    val books by viewModel.books.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.errorMessage.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllBooks()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            error != null && error?.isNotEmpty() == true -> {
                Text(
                    text = "Error al cargar los libros",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            books.isNullOrEmpty() -> {
                Text(
                    text = "No hay libros disponibles",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn {
                    items(books!!.size) { index ->
                        val book = books!![index]
                        BookCard(book = book, onClick = { onBookSelected(book.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title,
                style = MaterialTheme.typography.titleLarge )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = book.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = book.createdAt,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Géneros: ${book.genres.joinToString(", ")}")
        }
    }
}
