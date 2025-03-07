package com.example.watpato.profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.model.subscriptions.BookSubscription
import com.example.watpato.profile.data.model.subscriptions.UserSubscription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    userId: Int,
    navController: NavController
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Libros", "Usuarios", "Perfil")
    val icons = listOf(Icons.Filled.Book, Icons.Filled.Group, Icons.Filled.Person)

    val bookSubscriptions by viewModel.bookSubscriptions.observeAsState()
    val userSubscriptions by viewModel.userSubscriptions.observeAsState()
    val booksByUser by viewModel.booksByUser.observeAsState()

    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
        viewModel.loadSubscriptions(userId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("Home") }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = icons[index], contentDescription = item) },
                        label = { Text(item) },
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
                2 -> UserProfileView(booksByUser, navController)
            }
        }
    }
}

@Composable
fun BookSubscriptionsView(bookSubscriptions: Result<BookSubscription>?, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Biblioteca",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        bookSubscriptions?.fold(
            onSuccess = { subscription ->
                LazyColumn {
                    items(subscription.subscriptions) { book ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    navController.navigate("BookPreview/${book.id}")
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = book.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = book.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            },
            onFailure = {
                Text(text = "Error al cargar los libros suscritos")
            }
        )
    }
}

@Composable
fun UserSubscriptionsView(userSubscriptions: Result<UserSubscription>?, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Usuarios que sigues",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        userSubscriptions?.fold(
            onSuccess = { subscription ->
                LazyColumn {
                    items(subscription.subscriptions) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    navController.navigate("Profile/${user.id}")
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Usuario",
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                                Text(
                                    text = user.username,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            },
            onFailure = {
                Text(text = "Error al cargar los usuarios suscritos")
            }
        )
    }
}

@Composable
fun UserProfileView(userProfile: Result<UserProfile>?, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userProfile?.fold(
            onSuccess = { profile ->
                Text(
                    text = profile.user.username,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Libros escritos",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(profile.books) { book ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {
                                    navController.navigate("BookPreview/${book.id}")
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = book.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = book.description,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            },
            onFailure = {
                Text(text = "Error al cargar el perfil del usuario")
            }
        )
    }
}
