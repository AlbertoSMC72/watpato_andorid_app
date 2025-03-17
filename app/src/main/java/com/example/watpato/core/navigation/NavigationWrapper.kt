package com.example.watpato.core.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watpato.features.forms.addBook.presentation.CreateBookScreen
import com.example.watpato.features.forms.addBook.presentation.CreateBookViewModel
import com.example.watpato.features.authorization.login.presentation.LoginScreen
import com.example.watpato.features.authorization.login.presentation.LoginViewModel
import com.example.watpato.features.authorization.register.presentation.RegisterScreen
import com.example.watpato.features.authorization.register.presentation.RegisterViewModel
import com.example.watpato.features.views.BookView.presentation.BookPreviewScreen
import com.example.watpato.features.views.BookView.presentation.BookPreviewViewModel
import com.example.watpato.features.views.ChapterView.presentation.ChapterScreen
import com.example.watpato.features.views.ChapterView.presentation.ChapterViewModel
import com.example.watpato.features.forms.addChapter.presentation.CreateChapterScreen
import com.example.watpato.features.forms.addChapter.presentation.CreateChapterViewModel
import com.example.watpato.core.data.UserInfoProvider
import com.example.watpato.core.local.appDatabase.DatabaseProvider
import com.example.watpato.features.home.presentation.Home
import com.example.watpato.features.home.presentation.HomeViewModel
import com.example.watpato.features.views.profileView.domain.BookSubscriptionUseCase
import com.example.watpato.features.views.profileView.domain.ProfileUseCase
import com.example.watpato.features.views.profileView.domain.UserSubscriptionUseCase
import com.example.watpato.features.views.profileView.presentation.ProfileScreen
import com.example.watpato.features.views.profileView.presentation.ProfileViewModel
import com.example.watpato.features.views.writerView.WriterView
import com.example.watpato.features.views.writerView.WriterViewModel

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
            val context = LocalContext.current.applicationContext
            val database = DatabaseProvider.getAppDataBase(context)
            val chapterDAO = database.chapterDAO()
            ProfileScreen(
                viewModel = ProfileViewModel(bookSubscriptionUseCase, userSubscriptionUseCase, profileUseCase, chapterDAO),
                userId = writerId,
                navController = navController
            )
        }

        composable(
            route = "Chapter/{chapterId}",
            arguments = listOf(navArgument("chapterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 0
            val context = LocalContext.current.applicationContext
            val application = context.applicationContext as Application
            ChapterScreen(viewModel = ChapterViewModel(application), chapterId = chapterId)
        }

        composable("CreateChapter/{bookId}") { backStackEntry ->
            val bookIdParam = backStackEntry.arguments?.getString("bookId")?.toInt() ?: -1
            CreateChapterScreen(CreateChapterViewModel(bookIdParam)) { destination ->
                navController.navigate(destination)
            }
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