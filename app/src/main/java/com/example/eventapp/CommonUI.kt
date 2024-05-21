package com.example.eventapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eventapp.ui.theme.*

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