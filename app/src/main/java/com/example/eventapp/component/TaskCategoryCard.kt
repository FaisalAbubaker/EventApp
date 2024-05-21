package com.example.eventapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowForward
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventapp.R
import com.example.eventapp.data.entity.TaskType


@Composable
fun TaskCategoryCard(
    title: String,
    subTitle: String,
    tintColor: Color,
    height: Dp = 180.dp,
    onClick: () -> Unit,
    image: @Composable () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                onClick.invoke()
            }
            .shadow(elevation = 22.dp, shape = RoundedCornerShape(16.dp), spotColor = tintColor)
        ,
        colors = CardDefaults.cardColors(
            containerColor = tintColor
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.home_tag_background),
                contentDescription = null, // Set appropriate content description
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.TopEnd), // Position image at top-right corner
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(tintColor)
            )

            // Your content here
            Column(
                modifier = Modifier
                    .padding(16.dp), // Align content to the start of the box
            ) {
                // Your content goes here

                Row(
                    modifier = Modifier
                        .shadow(
                            44.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White,
                        )
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    image.invoke()
                    Icon(imageVector = Icons.TwoTone.ArrowForward, contentDescription = "")
                }
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = title,
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(text = subTitle)

            }
        }
    }
}

@Preview
@Composable
fun TaskCategoryCardPreview() {

    Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

        Column(modifier = Modifier.weight(0.4f).padding(vertical =12.dp)) {
            TaskCategoryCard(
                TaskType.Completed.type,
                "15 Task",
                Color(0xFF7DC8E7),
                height = 220.dp,
                onClick = {},
                image = {
                    Image(
                        painter = painterResource(id = R.drawable.imac),
                        contentDescription = "",
                        modifier = Modifier.size(80.dp)
                    )

                })
            TaskCategoryCard(
                TaskType.Pending.type,
                "15 Task",
                Color(0xFF7D88E7),
                height = 190.dp,
                onClick = {},
                image = {
                    Icon(
                        imageVector = Icons.TwoTone.CheckCircle,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)

                    )
                })
        }
        /////
        Column(modifier = Modifier.weight(0.4f).padding(vertical = 12.dp)) {
            TaskCategoryCard(
                TaskType.Cancelled.type,
                "15 Task",
                Color(0xFFE77D7D),
                height = 190.dp,
                onClick = {},
                image = {
                    Icon(
                        imageVector = Icons.TwoTone.CheckCircle,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)

                    )
                })
            TaskCategoryCard(
                TaskType.OnGoing.type,
                "15 Task",
                Color(0xFF81E89E),
                height = 220.dp,
                onClick = {},
                image = {
                    Image(
                        painter = painterResource(id = R.drawable.imac),
                        contentDescription = "",
                        modifier = Modifier.size(90.dp)
                    )
                })
        }

    }
}