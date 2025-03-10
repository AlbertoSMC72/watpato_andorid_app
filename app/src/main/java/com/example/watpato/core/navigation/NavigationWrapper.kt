package com.example.watpato.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watpato.addBook.presentation.CreateBookScreen
import com.example.watpato.addBook.presentation.CreateBookViewModel
import com.example.watpato.login.presentation.LoginScreen
import com.example.watpato.login.presentation.LoginViewModel
import com.example.watpato.register.presentation.RegisterScreen
import com.example.watpato.register.presentation.RegisterViewModel
import com.example.watpato.BookPreview.presentation.BookPreviewScreen
import com.example.watpato.BookPreview.presentation.BookPreviewViewModel
import com.example.watpato.ChapterView.presentation.ChapterScreen
import com.example.watpato.ChapterView.presentation.ChapterViewModel
import com.example.watpato.addChapter.presentation.CreateChapterScreen
import com.example.watpato.addChapter.presentation.CreateChapterViewModel
import com.example.watpato.core.data.UserInfoProvider
import com.example.watpato.home.presentation.Home
import com.example.watpato.home.presentation.HomeViewModel
import com.example.watpato.profile.domain.BookSubscriptionUseCase
import com.example.watpato.profile.domain.ProfileUseCase
import com.example.watpato.profile.domain.UserSubscriptionUseCase
import com.example.watpato.profile.presentation.ProfileScreen
import com.example.watpato.profile.presentation.ProfileViewModel
import com.example.watpato.writerView.WriterView
import com.example.watpato.writerView.WriterViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val bookSubscriptionUseCase = BookSubscriptionUseCase()
    val userSubscriptionUseCase = UserSubscriptionUseCase()
    val profileUseCase = ProfileUseCase()
    val userId = UserInfoProvider.userID

    NavHost(navController = navController, startDestination = "Login") {

        composable("Login") {
            LoginScreen(LoginViewModel()) { destination ->
                navController.navigate(destination)
            }
        }
        composable("Register") {
            RegisterScreen(RegisterViewModel()) { destination ->
                navController.navigate(destination)
            }
        }

        composable("AddBook") {
            CreateBookScreen(CreateBookViewModel()) { destination ->
                navController.navigate(destination)
            }
        }
        composable("Home") {
            Home(HomeViewModel(), navController) { bookId ->
                navController.navigate("BookPreview/$bookId")
            }
        }

        composable(
            route = "BookPreview/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            BookPreviewScreen(BookPreviewViewModel(), bookId, navController)
        }

        composable(
            route = "Profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val writerId = backStackEntry.arguments?.get("userId").toString().toInt()
            ProfileScreen(
                viewModel = ProfileViewModel(bookSubscriptionUseCase, userSubscriptionUseCase, profileUseCase),
                userId = writerId,
                navController = navController
            )
        }

        composable(
            route = "Chapter/{chapterId}",
            arguments = listOf(navArgument("chapterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 0
            ChapterScreen(viewModel = ChapterViewModel(), chapterId = chapterId)
        }

        composable("CreateChapter/{bookId}") { backStackEntry ->
            val viewModel = CreateChapterViewModel()
            val bookIdParam = backStackEntry.arguments?.getString("bookId")?.toInt() ?: -1
            CreateChapterScreen(
                viewModel = viewModel,
                bookId = bookIdParam,
                onChapterCreated = {
                    navController.navigate("Chapter/$bookIdParam")
                }
            )
        }

        composable("WriterProfile/{authorId}", arguments = listOf(navArgument("authorId") { type = NavType.IntType })) { backStackEntry ->
            val authorId = backStackEntry.arguments?.getInt("authorId") ?: 0
            WriterView(
                viewModel = WriterViewModel(ProfileUseCase()),
                authorId = authorId,
                navController = navController
            )
        }
    }
}