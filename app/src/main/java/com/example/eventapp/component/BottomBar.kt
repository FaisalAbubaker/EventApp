package com.example.eventapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventapp.navigation.BottomNavigationItem
import com.example.eventapp.navigation.Screens
import com.example.eventapp.navigation.popUpToTop
import com.example.eventapp.ui.theme.PrimaryColor

@Composable
fun BottomBar(navController: NavHostController){
    val navigationSelectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
        contentAlignment = Alignment.BottomCenter){
        Row(modifier = Modifier.shadow(16.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
            BottomNavigationItem().bottomNavigationItems()
                .forEachIndexed { index, bottomNavigationItem ->
                    if(bottomNavigationItem.route == Screens.MainApp.AddScreen.route){
                        Icon(
                            bottomNavigationItem.icon,
                            "",
                            modifier = Modifier.size(75.dp)
                                .semantics {
                                    testTag = Screens.MainApp.AddScreen.route
                                }
                                .clickable {
                                    navigationSelectedItem.intValue = index
                                    navController.navigate(bottomNavigationItem.route){
                                        popUpToTop(navController)
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            tint = PrimaryColor
                        )
                    } else {
                        Icon(
                            bottomNavigationItem.icon,
                            "",
                            modifier = Modifier
                                .semantics {
                                    testTag = bottomNavigationItem.route
                            }
                                .clickable {
                                    navigationSelectedItem.intValue = index
                                    navController.navigate(bottomNavigationItem.route){
                                        popUpToTop(navController)
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                .padding(12.dp),
                            tint = if(navigationSelectedItem.intValue == index) PrimaryColor else Color.Gray.copy(0.5f)
                        )
                    }
                }
        }
    }
}