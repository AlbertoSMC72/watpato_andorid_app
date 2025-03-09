package com.example.watpato.addChapter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watpato.ui.theme.Beige
import com.example.watpato.ui.theme.Teal

@Composable
fun CreateChapterScreen(
    viewModel: CreateChapterViewModel,
    bookId: Int,
    onChapterCreated: () -> Unit
) {
    val title by viewModel.title.observeAsState("")
    val content by viewModel.content.observeAsState("")
    val error by viewModel.error.observeAsState("")
    val success by viewModel.success.observeAsState(false)

    LaunchedEffect(bookId) {
        viewModel.setBookId(bookId)
    }

    if (success) {
        onChapterCreated()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Teal)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Capítulo",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Beige
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = title,
            onValueChange = { viewModel.onTitleChange(it) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = content,
            onValueChange = { viewModel.onContentChange(it) },
            label = { Text("Contenido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(text = error, color = Beige)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { viewModel.createChapter() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Beige,
                contentColor = Teal
            )
        ) {
            Text(text = "Crear Capítulo")
        }
    }
}