package com.example.watpato.addBook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watpato.ui.theme.Beige
import com.example.watpato.ui.theme.Teal

@Composable
fun CreateBookScreen(
    viewModel: CreateBookViewModel,
    onNavigate: (String) -> Unit
) {
    val title by viewModel.title.observeAsState("")
    val description by viewModel.description.observeAsState("")
    val genres by viewModel.genres.observeAsState(emptyList())
    val selectedGenreIds by viewModel.selectedGenreIds.observeAsState(emptyList())
    val error by viewModel.error.observeAsState("")
    val success by viewModel.success.observeAsState(false)

    // Estado para el diálogo de creación de género
    val showDialog = remember { mutableStateOf(false) }
    // Nombre temporal para el nuevo género
    val newGenreName = remember { mutableStateOf("") }


    // Si se crea el libro con éxito, vuelve o navega a "Home"
    if (success) {
        onNavigate("Home")
    }

    // Modal (AlertDialog) para crear un nuevo género
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Crear nuevo género") },
            text = {
                TextField(
                    value = newGenreName.value,
                    onValueChange = { newGenreName.value = it },
                    label = { Text("Nombre del género") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.createNewGenre(newGenreName.value)
                        newGenreName.value = ""
                        showDialog.value = false
                    }
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Teal)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Libro",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Beige,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title,
            onValueChange = { viewModel.onTitleChange(it) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = description,
            onValueChange = { viewModel.onDescriptionChange(it) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para cargar géneros manualmente
        Button(
            onClick = {
                viewModel.loadGenres()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Beige,
                contentColor = Teal
            )
        ) {
            Text(text = "Cargar Géneros Guardados")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Selecciona Géneros:", color = Beige)
        Spacer(modifier = Modifier.height(8.dp))

        genres.forEach { genre ->
            val isSelected = selectedGenreIds.contains(genre.id)
            Text(
                text = if (isSelected) "✔ ${genre.name}" else genre.name,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { viewModel.onGenreSelected(genre.id) }
                    .padding(vertical = 4.dp),
                color = Beige
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para abrir el diálogo de crear género
        Button(
            onClick = { showDialog.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Beige,
                contentColor = Teal
            )
        ) {
            Text(text = "Crear nuevo Género")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (error.isNotEmpty()) {
            Text(text = error, color = Beige)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { viewModel.createBook() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Beige,
                contentColor = Teal
            )
        ) {
            Text(text = "Crear Libro")
        }
    }
}