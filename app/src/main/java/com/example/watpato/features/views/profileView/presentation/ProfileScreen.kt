package com.example.watpato.features.views.profileView.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.MenuBook
import com.example.watpato.features.views.profileView.presentation.composables.UserProfileView
import com.example.watpato.features.views.profileView.presentation.composables.UserSubscriptionsView
import com.example.watpato.features.views.profileView.presentation.composables.BookSubscriptionsView
import com.example.watpato.features.views.profileView.presentation.composables.DownloadedChaptersView

val DarkPurple = Color(0xFF543F69)
val LightPurple = Color(0xFFE6DFEB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    userId: Int,
    navController: NavController
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Libros", "Usuarios", "Perfil", "Descargas")
    val icons = listOf(Icons.Filled.Book, Icons.Filled.Group, Icons.Filled.Person, Icons.Filled.MenuBook)

    val bookSubscriptions by viewModel.bookSubscriptions.observeAsState()
    val userSubscriptions by viewModel.userSubscriptions.observeAsState()
    val booksByUser by viewModel.booksByUser.observeAsState()
    val downloadedChapters by viewModel.downloadedChapters.observeAsState()

    Log.d("BookPreviewScreen", "Data received: userId: $userId")

    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
        viewModel.loadSubscriptions(userId)
        viewModel.loadDownloadedChapters()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = Color.White) },
                actions = {
                    IconButton(onClick = { navController.navigate("Home") }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkPurple)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkPurple
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = icons[index], contentDescription = item, tint = Color.White) },
                        label = { Text(item, color = Color.White) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                0 -> BookSubscriptionsView(bookSubscriptions, navController)
                1 -> UserSubscriptionsView(userSubscriptions, navController)
                2 -> UserProfileView(userProfile = booksByUser, navController = navController)
                3 -> DownloadedChaptersView(downloadedChapters)
            }
        }
    }
}
