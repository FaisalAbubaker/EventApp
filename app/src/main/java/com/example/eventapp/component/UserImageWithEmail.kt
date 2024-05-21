package com.example.eventapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eventapp.R
import com.example.eventapp.ui.theme.Navy
import com.google.firebase.auth.FirebaseUser

@Composable
fun UserImageWithEmail(user: FirebaseUser?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(20),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                ),

                ) {
                Icon(
                    Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                        .padding(16.dp),
                )
            }
        }
        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(50),//use 20 if you want to round corners like the one in the design
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),

            ) {

            if (user?.photoUrl.toString().isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.user_avatar_male),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = user?.photoUrl,
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }


        Text(
            user?.displayName.orEmpty(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Navy
        )
        Text(
            user?.email.orEmpty(),
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Preview
@Composable
fun UserImageWithEmailPreview() {
    UserImageWithEmail(null)
}