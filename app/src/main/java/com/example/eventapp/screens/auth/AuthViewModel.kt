package com.example.eventapp.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.eventapp.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val auth = Firebase.auth
    var isSignedIn =
        if(auth.currentUser == null) mutableStateOf(Screens.Authentication.route) else mutableStateOf(Screens.MainApp.route)
    val error = mutableStateOf("")

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                } else {
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }

    fun logout(){
        auth.signOut()
        isSignedIn.value = Screens.Authentication.route
    }

    fun signup(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){

                } else {
                    val exception = task.exception
                    error.value = exception?.message.orEmpty()
                }
            }
    }

    fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                } else {
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }


}