package com.example.eventapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eventapp.component.BottomBar
import com.example.eventapp.navigation.EventAppNavigation
import com.example.eventapp.navigation.Screens
import com.example.eventapp.screens.auth.AuthViewModel
import com.example.eventapp.ui.theme.EventAppTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

//class MainActivity : ComponentActivity()
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            EventAppTheme {
                val navController = rememberNavController()

                var showBottomBar by rememberSaveable { mutableStateOf(false) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when(navBackStackEntry?.destination?.route){
                    Screens.MainApp.Home.route -> true
                    Screens.MainApp.AddScreen.route -> true
                    Screens.MainApp.TaskByDate.route -> true
                    Screens.MainApp.CategoryScreen.route -> true
                    Screens.MainApp.StaticsScreen.route -> true
                    else -> false
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            contentDescription = "MyScreen"
                        },
                    ){ paddingValues ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)){
                        if(authViewModel.error.value.isNotEmpty()){
                            Snackbar(modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                                containerColor = Color.Red.copy(0.5f)) {
                                Text(text = authViewModel.error.value)
                            }
                        }
                        EventAppNavigation(authViewModel, navController)
                    }
                    if(showBottomBar){
                        BottomBar(navController)
                    }
                }
//                EventAppNavigation()
//                Column(Modifier.padding(15.dp)) {
//
//                    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
//                    val launcher = rememberFirebaseAuthLauncher(
//                        onAuthComplete = { result ->
//                            user = result.user
//                        },
//                        onAuthError = {
//                            user = null
//                            println(it.message)
//                        }
//                    )
//                    val token =
//                        "884104348545-hse5bjg63prcg850b990c1d0953tcum2.apps.googleusercontent.com"
//                    val context = LocalContext.current
//
//                    if (user == null){
//                        Text("Not logged in")
//                        Button(onClick = {
//                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestIdToken(token)
//                                .requestEmail()
//                                .build()
//                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
//                            launcher.launch(googleSignInClient.signInIntent)
//                        }) {
//                            Text(text = "Sign in via Google")
//                        }
//                    }else {
//                        Text(text = "Welcome ${user!!.displayName}")
//                        AsyncImage(
//                            model = user!!.photoUrl,
//                            contentDescription =null,
//                            Modifier
//                                .clip(CircleShape)
//                                .size(45.dp))
//                        Button(onClick = {
//                            Firebase.auth.signOut()
//                            user = null
//                        }) {
//                            Text(text = "Sign out")
//                        }
//                    }
//                }
            }
        }
    }
}


//@Composable
//fun rememberFirebaseAuthLauncher(
//    onAuthComplete: (AuthResult) -> Unit,
//    onAuthError: (ApiException) -> Unit
//): ManagedActivityResultLauncher<Intent, ActivityResult>{
//    val scope = rememberCoroutineScope()
//    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(ApiException::class.java)
//            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//            scope.launch {
//                val authResult = Firebase.auth.signInWithCredential(credential).await()
//                onAuthComplete(authResult)
//            }
//        } catch (e: ApiException){
//            onAuthError(e)
//        }
//    }
//}

