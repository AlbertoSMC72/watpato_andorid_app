package com.example.watpato.BookPreview.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.watpato.BookPreview.data.model.entities.Book
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BookPreviewScreen(
    viewModel: BookPreviewViewModel,
    bookId: Int,
    userId: Int,
    navController: NavController
) {
    val book by viewModel.book.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.errorMessage.observeAsState()
    val isSubscribed by viewModel.isSubscribed.observeAsState(initial = false)

    Log.d("BookPreviewScreen", "Data received: book: $book, isLoading: $isLoading, error: $error, isSubscribed: $isSubscribed, userId: $userId, bookId: $bookId")

    LaunchedEffect(key1 = bookId) {
        viewModel.getBook(bookId)
        viewModel.checkSubscription(userId, bookId)
    }

    Log.d("BookPreviewScreen", "isSubscribed: $isSubscribed")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            error != null && error?.isNotEmpty() == true -> {
                Text(
                    text = "Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            book != null -> {
                BookContent(
                    book = book!!,
                    navController = navController,
                    isSubscribed = isSubscribed,
                    onSubscriptionToggle = { viewModel.toggleSubscription(userId, bookId) }
                )
            }
        }
    }
}

@Composable
fun BookContent(
    book: Book,
    navController: NavController,
    isSubscribed: Boolean,
    onSubscriptionToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onSubscriptionToggle,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSubscribed) Color.Red else Color.Green
            ),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = if (isSubscribed) "Dejar de seguir" else "Suscribir"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = book.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Géneros:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = book.genres.joinToString(", "))

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Capítulos:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        book.chapters.forEach { chapter ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        navController.navigate("Chapter/${chapter.id}")
                    }
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
