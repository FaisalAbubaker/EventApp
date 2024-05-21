package com.example.eventapp.ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.eventapp.navigation.EventAppNavigation
import com.example.eventapp.navigation.Screens
import com.example.eventapp.screens.auth.AuthViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestNavigation {
    @get: Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel

    private lateinit var navController: TestNavHostController

    @Before
    fun init(){
        hiltRule.inject()
        composeTestRule.setContent {
            authViewModel = hiltViewModel()
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(DialogNavigator())
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            EventAppNavigation(authViewModel = authViewModel, navController = navController)
        }
    }

    @Test
    fun testStartDestination(){
        if(authViewModel.auth.currentUser!=null){
            assertEquals(
                Screens.MainApp.Home.route,
                navController.currentBackStackEntry?.destination?.route
            )
        }else{
            assertEquals(
                Screens.Authentication.Splash.route,
                navController.currentBackStackEntry?.destination?.route
            )
        }
    }

    @Test
    fun testStartDestinationIfUserLoggedIn(){
        assertEquals(
            authViewModel.isSignedIn.value,
            navController.currentBackStackEntry?.destination?.parent?.route
        )
    }
}