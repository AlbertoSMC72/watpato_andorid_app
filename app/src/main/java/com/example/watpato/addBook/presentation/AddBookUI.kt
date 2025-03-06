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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    // Observamos los estados
    val title by viewModel.title.observeAsState("")
    val description by viewModel.description.observeAsState("")
    val genres by viewModel.genres.observeAsState(emptyList())
    val selectedGenreIds by viewModel.selectedGenreIds.observeAsState(emptyList())
    val error by viewModel.error.observeAsState("")
    val success by viewModel.success.observeAsState(false)

    // Al iniciar la pantalla cargamos géneros
    LaunchedEffect(Unit) {
        viewModel.loadGenres()
    }

    // Si se creó correctamente el libro, podrías navegar o limpiar la UI
    if (success) {
        // Ejemplo: navegas a otra pantalla
        // onNavigate("BookList")
        // O reinicias el formulario
        // ...
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

        // Muestra la lista de géneros disponibles
        Text(text = "Selecciona Géneros:", color = Beige)
        Spacer(modifier = Modifier.height(8.dp))

        genres.forEach { genre ->
            val isSelected = selectedGenreIds.contains(genre.id)
            // Podrías poner un Row con checkbox, etc.
            // Aquí un ejemplo sencillo con un clickable:
            Text(
                text = if (isSelected) "✔ ${genre.name}" else genre.name,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
                        viewModel.onGenreSelected(genre.id)
                    }
                    .padding(vertical = 4.dp),
                color = Beige
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para crear un nuevo género si no aparece en la lista
        Button(
            onClick = {
                // Por ejemplo, generas un nuevo género con un nombre random
                // O usas un showDialog para ingresar nombre
                viewModel.createNewGenre("NuevoGeneroEjemplo")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Beige,
                contentColor = Teal
            )
        ) {
            Text(text = "Crear nuevo Género")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar errores
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Beige
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botón final para crear el libro
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