package com.example.eventapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eventapp.ui.theme.LightBlue
import com.example.eventapp.ui.theme.LightGreen
import com.example.eventapp.ui.theme.LightOrange
import com.example.eventapp.ui.theme.LightPurple
import com.example.eventapp.ui.theme.LightRed
import com.example.eventapp.ui.theme.PrimaryColor

fun iconByName(name: String): ImageVector {
    try {
        val cl = Class.forName("androidx.compose.material.icons.outlined.${name}Kt")
        val method = cl.declaredMethods.first()
        return method.invoke(null, Icons.Outlined) as ImageVector
    }catch (e:Exception){
        return Icons.Outlined.Info
    }
}

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun getAllSystemIcons(): Set<ImageVector> {
    return setOf(
        // Outlined Icons
        Icons.Outlined.AccountCircle,
        Icons.Outlined.Add,
        Icons.Outlined.Home,
        Icons.Outlined.Info,
        Icons.Outlined.Build,
        Icons.Outlined.DateRange,
        Icons.Outlined.CheckCircle,
        Icons.Outlined.Close,
        Icons.Outlined.Delete,
        Icons.Outlined.Edit,
        Icons.Outlined.Email,
        Icons.Outlined.Person,
        Icons.Outlined.Favorite,
        Icons.Outlined.Lock,
        Icons.Outlined.Menu,
        Icons.Outlined.Notifications,
        Icons.Outlined.Search,
        Icons.Outlined.Share,
        Icons.Outlined.Settings,
        Icons.Outlined.Star,
        Icons.Outlined.ThumbUp,
        Icons.Outlined.Favorite,
        Icons.Outlined.Send,
        Icons.Outlined.Call
    )
}

fun getAllColors() = listOf(
    LightRed,
    LightBlue,
    LightGreen,
    LightPurple,
    PrimaryColor,
    LightOrange,
)