package com.example.eventapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String) {
    data object MainApp : Screens("mainGraph") {
        data object Home : Screens("home_screen")
        data object TaskByDate : Screens("task_by_date_screen")
        data object AddScreen : Screens("add_screen")
        data object CategoryScreen : Screens("category_screen")
        data object StaticsScreen : Screens("Statics_screen")
        data object DateDialog: Screens("DateDialog")
        data object AddTagDialog: Screens("AddTagDialog")
        data object TaskByCategory: Screens("TaskByCategory")
        data object UpdateTaskScreen: Screens("update_task_screen")

        data object SettingsScreen: Screens("settings_screen")

    }

    data object Authentication : Screens("authGraph") {
        data object Splash : Screens("splash")
        data object SignUp : Screens("signup_route")
        data object Login : Screens("login_route")
    }
}

data class BottomNavigationItem(
    val icon: ImageVector = Icons.Filled.Home,
    val route : String = ""
){

    fun bottomNavigationItems() : List<BottomNavigationItem>{
        return listOf(
            BottomNavigationItem(
                icon = Icons.Outlined.Home,
                route = Screens.MainApp.Home.route
            ),
            BottomNavigationItem(
                icon = Icons.Outlined.List,
                route = Screens.MainApp.TaskByDate.route
            ),
            BottomNavigationItem(
                icon = Icons.Outlined.AddCircle,
                route = Screens.MainApp.AddScreen.route
            ),
            BottomNavigationItem(
                icon = Icons.Outlined.Settings,
                route = Screens.MainApp.CategoryScreen.route
            ),
            BottomNavigationItem(
                icon = Icons.Outlined.DateRange,
                route = Screens.MainApp.StaticsScreen.route
            ),
        )
    }
}