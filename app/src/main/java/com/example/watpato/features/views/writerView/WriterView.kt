package com.example.watpato.features.views.writerView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.watpato.core.data.UserInfoProvider

val DarkPurple = Color(0xFF543F69)
val Follow = Color(0xFF81D32F)
val Unfollow = Color(0xFFD32F2F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriterView(
    viewModel: WriterViewModel,
    authorId: Int,
    navController: NavController
) {
    val userProfile by viewModel.userProfile.observeAsState()
    val isSubscribed by viewModel.isSubscribed.observeAsState(initial = false)
    val userId = UserInfoProvider.userID

    LaunchedEffect(authorId) {
        viewModel.loadAuthorBooks(authorId)
        viewModel.checkSubscription(userId, authorId)
    }

    Scaffold(
        topBar = {
            userProfile?.getOrNull()?.let { profile ->
                TopAppBar(
                    title = { Text(text = profile.user.username, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                        }
                    },
                    actions = {
                        Button(
                            onClick = { viewModel.toggleSubscription(userId, authorId) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSubscribed) Unfollow else Follow
                            )
                        ) {
                            Text(text = if (isSubscribed) "Dejar de seguir" else "Seguir")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkPurple)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            userProfile?.fold(
                onSuccess = { profile ->
                    LazyColumn {
                        items(profile.books) { book ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { navController.navigate("BookPreview/${book.id}") }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = book.title, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = book.description)
                                }
                            }
                        }
                    }
                },
                onFailure = {
                    Text(text = "Error al cargar el perfil del autor", color = MaterialTheme.colorScheme.error)
                }
            )
        }
    }
}
