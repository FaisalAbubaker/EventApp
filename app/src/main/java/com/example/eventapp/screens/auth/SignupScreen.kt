package com.example.eventapp.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eventapp.R
import com.example.eventapp.component.LoginWithGoogle
import com.example.eventapp.navigation.Screens
import com.example.eventapp.ui.theme.PrimaryColor

@Composable
fun SignUpScreen(navController: NavHostController, authViewModel: AuthViewModel){
    val usernameState = remember { mutableStateOf(TextFieldValue()) }
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    Column(Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.sign_up), fontSize = 36.sp, modifier = Modifier.padding(top = 100.dp, start = 36.dp),
            fontWeight = FontWeight.Bold, color = PrimaryColor
        )
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = usernameState.value, onValueChange = { usernameState.value = it },
                label = { Text(text = stringResource(id = R.string.username)) },
                leadingIcon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "") },
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 20.dp)
                    .width(304.dp),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White)
            )
            TextField(
                value = emailState.value, onValueChange = { emailState.value = it },
                label = { Text(text = stringResource(id = R.string.email)) },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "") },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .width(304.dp),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White)
            )
            TextField(value = passwordState.value, onValueChange = { passwordState.value = it },
                label = { Text(stringResource(id = R.string.password)) },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "") },
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .width(304.dp),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = {
                authViewModel.signup(
                    emailState.value.text,
                    passwordState.value.text,
                    usernameState.value.text
                )
            }, modifier = Modifier.padding(bottom = 50.dp)
                .width(304.dp)
                .height(52.dp)
            ) {
                Text(text = stringResource(id = R.string.create), fontSize = 18.sp, color = Color.White)
            }
            Row(Modifier.padding(start = 30.dp, end = 30.dp, bottom = 5.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .background(Color.Gray)) {}
                Text(
                    text = stringResource(id = R.string.or_with), modifier = Modifier.weight(1f),
                    style = TextStyle(
                        textAlign = TextAlign.Center
                    ),
                )
                Box(modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .background(Color.Gray)) {}
            }
            LoginWithGoogle()
            Row(Modifier.padding(top = 80.dp)) {
                Text(text = stringResource(id = R.string.already))
                Text(
                    text = stringResource(id = R.string.login),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(Screens.Authentication.Login.route) })
            }
        }
    }
}