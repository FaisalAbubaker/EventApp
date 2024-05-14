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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.eventapp.screens.auth.AuthViewModel
import com.example.eventapp.screens.auth.LoginScreen
import com.example.eventapp.screens.auth.SignUpScreen
import com.example.eventapp.screens.auth.SplashScreen
import com.example.eventapp.screens.task.TaskViewModel
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
        route = Screens.MainApp.route
    ){
        composable(Screens.MainApp.Home.route){
            Column(Modifier.fillMaxSize()){
                Text(text = "Hello ${userName.invoke()?.displayName.orEmpty()}")
            }
        }
        composable(Screens.MainApp.TaskByDate.route){
            val viewmodel: TaskViewModel = hiltViewModel()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item{
                    val result = viewmodel.tasks.collectAsState(null)
                    val tags = viewmodel.tags.collectAsState(null)
                    val tasksByTag = viewmodel.tasksByTags.collectAsState(null)
                    
                    Text(text = result.value.toString())
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    Text(text = tasksByTag.value.toString())
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    Text(text = tags.value.toString())
                }
                
            }
        }
        composable(Screens.MainApp.CategoryScreen.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red)){
                Button(onClick = { logout.invoke() }) {
                    Text(text = "Logout", color = Color.White)
                }
            }
        }
        composable(Screens.MainApp.AddScreen.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Magenta)){

            }
        }
        composable(Screens.MainApp.StaticsScreen.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green)){

            }
        }
    }
}
fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}