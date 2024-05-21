package com.example.eventapp.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventapp.component.MonthlyHorizontalCalendarView
import com.example.eventapp.screens.auth.AuthViewModel
import com.example.eventapp.screens.auth.LoginScreen
import com.example.eventapp.screens.auth.SignUpScreen
import com.example.eventapp.screens.auth.SplashScreen
import com.example.eventapp.screens.task.AddTagDialog
import com.example.eventapp.screens.task.AddTaskScreen
import com.example.eventapp.screens.task.AddTaskViewModel
import com.example.eventapp.screens.task.CategoryScreen
import com.example.eventapp.screens.task.HomeScreen
import com.example.eventapp.screens.task.TaskByDateScreen
import com.example.eventapp.screens.task.TaskViewModel
import com.example.eventapp.screens.task.TasksByCategory
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

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
            TaskByDateScreen(viewmodel)
        }
        composable(Screens.MainApp.CategoryScreen.route) {
            val taskViewModel: TaskViewModel = hiltViewModel()
            CategoryScreen(userName.invoke(), taskViewModel, navController, logout)
        }
        composable(Screens.MainApp.AddScreen.route) {
            val viewmodel: AddTaskViewModel = hiltViewModel()
            viewmodel.taskDate.value = it.savedStateHandle.get<String>("selectedDate").orEmpty()

            AddTaskScreen(navController, viewmodel)
        }
        composable(Screens.MainApp.StaticsScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {

            }
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
            val addTaskViewModel: AddTaskViewModel = hiltViewModel()
            AddTagDialog(navController, addTaskViewModel)
        }
        composable("${Screens.MainApp.TaskByCategory.route}/{tagName}", arguments = listOf(
            navArgument("tagName") {
                type = NavType.StringType
            }
        )) { navArgument ->
            val taskViewModel: TaskViewModel = hiltViewModel()
            val tagWithTaskLists = taskViewModel.tagWithTasks.value.firstOrNull {
                it.tag.name == navArgument.arguments?.getString(
                    "tagName"
                ).orEmpty()
            }
            val viewmodel: TaskViewModel = hiltViewModel()
            TasksByCategory(tagWithTaskLists, navController, viewModel = viewmodel)
        }
    }
}
fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}