package com.example.eventapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.eventapp.component.MonthlyHorizontalCalendarView
import com.example.eventapp.screens.auth.AuthViewModel
import com.example.eventapp.screens.auth.LoginScreen
import com.example.eventapp.screens.auth.SignUpScreen
import com.example.eventapp.screens.auth.SplashScreen
import com.example.eventapp.screens.task.AddTagDialog
import com.example.eventapp.screens.task.AddTaskScreen
import com.example.eventapp.screens.task.CategoryScreen
import com.example.eventapp.screens.task.HomeScreen
import com.example.eventapp.screens.task.SettingsScreen
import com.example.eventapp.screens.task.StatisticsScreen
import com.example.eventapp.screens.task.TaskByDateScreen
import com.example.eventapp.screens.task.TaskViewModel
import com.example.eventapp.screens.task.TasksByCategory
import com.example.eventapp.screens.task.UpdateTaskScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun EventAppNavigation(authViewModel: AuthViewModel, navController: NavHostController) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = authViewModel.isSignedIn.value
    ){
        authNavigation(navController, authViewModel)
        mainAppNavigation(navController, logout ={authViewModel.logout(context)} ){
            authViewModel.auth.currentUser
        }

    }
}

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
){
    navigation(
        startDestination = Screens.Authentication.Splash.route,
        route = Screens.Authentication.route
    ){
        composable(Screens.Authentication.Splash.route){
            SplashScreen(navController)
        }
        composable(Screens.Authentication.SignUp.route){
            SignUpScreen(navController, authViewModel)
        }
        composable(Screens.Authentication.Login.route){
            LoginScreen(navController, authViewModel)
        }
    }
}

fun NavGraphBuilder.mainAppNavigation(
    navController: NavHostController,
    logout: () -> Unit,
    userName: () -> FirebaseUser?
){
    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route,
    ) {
        composable(Screens.MainApp.Home.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            HomeScreen(userName.invoke(), navController, viewModel)
        }

        composable(Screens.MainApp.TaskByDate.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            TaskByDateScreen(viewmodel, navController)
        }
        composable(Screens.MainApp.CategoryScreen.route) {
            val taskViewModel: TaskViewModel = hiltViewModel()
            CategoryScreen(userName.invoke(), taskViewModel, navController, logout)
        }
        composable(Screens.MainApp.AddScreen.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            viewmodel.taskDate.value = it.savedStateHandle.get<String>("selectedDate").orEmpty()

            AddTaskScreen(navController, viewmodel)
        }
        composable(Screens.MainApp.StaticsScreen.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            StatisticsScreen()
        }
        composable(Screens.MainApp.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        dialog(
            Screens.MainApp.DateDialog.route, dialogProperties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {

            MonthlyHorizontalCalendarView(navController) {
                navController.popBackStack()
            }
        }
        dialog(
            Screens.MainApp.AddTagDialog.route, dialogProperties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            val taskViewModel: TaskViewModel = hiltViewModel()
            AddTagDialog(navController, taskViewModel)
        }
        composable("${Screens.MainApp.TaskByCategory.route}/{tagName}", arguments = listOf(
            navArgument("tagName") {
                type = NavType.StringType
            }
        )) { navArgument ->
            val viewmodel: TaskViewModel = hiltViewModel()
            TasksByCategory(navArgument.arguments?.getString("tagName"), navController, viewModel = viewmodel)
        }
        composable(
            route = "${Screens.MainApp.UpdateTaskScreen.route}/{task_id}",
            arguments = listOf(navArgument("task_id") { type = NavType.LongType })
        ) {
            val taskViewModel = hiltViewModel<TaskViewModel>()
            UpdateTaskScreen(navController, taskViewModel,
                it.arguments?.getLong("task_id"),
                it)
        }
    }
}
fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}

