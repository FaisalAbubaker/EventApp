package com.example.eventapp.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eventapp.R
import com.example.eventapp.navigation.Screens
import com.example.eventapp.ui.theme.PrimaryColor

@Composable
fun SplashScreen(navController: NavHostController){
    Column(Modifier.fillMaxSize().background(Color.White)
        .semantics {
                   testTag = "SplashScreen"
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.intro_image), contentDescription = "",
            Modifier
                .padding(top = 80.dp)
                .width(291.dp)
                .height(294.dp)
                .semantics {
                    testTag = "intro image"
                })
        Image(painter = painterResource(id = R.drawable.title), contentDescription = "",
            Modifier
                .padding(20.dp)
                .width(99.dp)
                .height(52.dp)
                .semantics {
                    testTag = "title text"
                })
        Text(text = "Plan what you will do to be more organized for today, tomorrow and beyond",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
                .semantics {
                    testTag = "description text"
                })
        
        Button(onClick = { navController.navigate(Screens.Authentication.Login.route) },
            modifier = Modifier
                .padding(top = 100.dp, bottom = 5.dp)
                .width(304.dp)
                .height(52.dp)
                .semantics {
                           testTag = "login button"
                },
            colors = ButtonDefaults.buttonColors(PrimaryColor)) {
            Text(text = "Login", fontSize = 18.sp, color = Color.White)
        }
        Button(onClick = { navController.navigate(Screens.Authentication.SignUp.route) },
            modifier = Modifier
                .padding(10.dp)
                .width(304.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(Color.White)){
            Text(text = "Sign Up", fontSize = 18.sp,
                color = Color(0xFF6A74CD))
        }

    }
}