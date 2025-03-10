package com.example.watpato.profile.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.watpato.profile.data.model.entities.UserProfile
import com.example.watpato.profile.data.model.subscriptions.BookSubscription
import com.example.watpato.profile.data.model.subscriptions.UserSubscription

val DarkPurple = Color(0xFF543F69)
val LightPurple = Color(0xFFE6DFEB)
val Follow = Color(0xFF81D32F)
val Unfollow = Color(0xFFD32F2F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    userId: Int,
    writerId: Int,
    navController: NavController
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Libros", "Usuarios", "Perfil")
    val icons = listOf(Icons.Filled.Book, Icons.Filled.Group, Icons.Filled.Person)

    val bookSubscriptions by viewModel.bookSubscriptions.observeAsState()
    val userSubscriptions by viewModel.userSubscriptions.observeAsState()
    val booksByUser by viewModel.booksByUser.observeAsState()
    val isSubscribed by viewModel.isSubscribed.observeAsState(initial = false)

    Log.d("BookPreviewScreen", "Data received: isSubscribed: $isSubscribed, userId: $userId, writerId: $writerId")

    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
        viewModel.loadSubscriptions(userId)
        viewModel.checkSubscription(userId, writerId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = Color.White) },
                navigationIcon = {
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
                2 -> UserProfileView(
                    userProfile = booksByUser,
                    navController = navController,
                    isSubscribed = isSubscribed,
                    onSubscriptionToggle = { viewModel.toggleSubscription(userId, writerId) }
                )
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
fun UserProfileView(
    userProfile: Result<UserProfile>?,
    navController: NavController,
    isSubscribed: Boolean,
    onSubscriptionToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userProfile?.fold(
            onSuccess = { profile ->

                Box(
                    modifier = Modifier
                        .background(color = LightPurple, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = profile.user.username,
                            style = MaterialTheme.typography.headlineMedium,
                            color = DarkPurple,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = onSubscriptionToggle,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSubscribed) Unfollow else Follow
                            )
                        ) {
                            Text(text = if (isSubscribed) "Dejar de seguir" else "Seguir")
                        }
                    }
                }

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

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )  {
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
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    text = book.description,
                                    style = MaterialTheme.typography.bodySmall,
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
