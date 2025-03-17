package com.example.watpato.features.views.ChapterView.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.watpato.features.views.ChapterView.data.model.Chapter
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.watpato.features.views.profileView.presentation.DarkPurple
import com.example.watpato.features.views.writerView.Follow

@Composable
fun ChapterScreen(
    viewModel: ChapterViewModel,
    chapterId: Int
) {
    val chapter by viewModel.chapter.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.errorMessage.observeAsState()
    val isSaved by viewModel.isSaved.observeAsState(initial = false)

    LaunchedEffect(key1 = chapterId) {
        viewModel.getChapter(chapterId)
    }

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
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            chapter != null -> {
                ChapterContent(
                    chapter = chapter!!,
                    onDownloadClick = { viewModel.saveChapter(chapter!!) }
                )
                if (isSaved) {
                    Box(
                        modifier = Modifier
                            .background(color = Follow, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Capítulo guardado :D",
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterContent(chapter: Chapter, onDownloadClick: () -> Unit) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = DarkPurple, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(
                text = chapter.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onDownloadClick()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DarkPurple)
        ) {
            Text("Descargar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Fecha de publicación",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = chapter.created_at,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = chapter.content,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}