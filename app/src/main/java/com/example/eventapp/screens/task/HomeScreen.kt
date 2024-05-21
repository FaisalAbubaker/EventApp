package com.example.eventapp.screens.task

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eventapp.R
import com.example.eventapp.component.TaskCard
import com.example.eventapp.component.TaskCategoryCard
import com.example.eventapp.data.entity.TaskType
import com.example.eventapp.navigation.Screens
import com.example.eventapp.ui.theme.Navy
import com.example.eventapp.ui.theme.PrimaryColor
import com.google.firebase.auth.FirebaseUser
import java.time.LocalDate


@Composable
fun HomeScreen(invoke: FirebaseUser?, navController: NavHostController, viewModel: TaskViewModel) {
    LaunchedEffect(Unit) {
        viewModel.sortTasksByDate(LocalDate.now().toString())
    }

    val completedTask = viewModel.completedTasks.collectAsState(initial = null)
    val cancelledTask = viewModel.cancelledTasks.collectAsState(initial = null)
    val onGoingTask = viewModel.onGoingTasks.collectAsState(initial = null)
    val pendingTask = viewModel.pendingTasks.collectAsState(initial = null)

    val tasksList = viewModel.taskWithTags

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 100.dp)
            .semantics {
                contentDescription = "Home Screen"
            }, verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            HeaderView(invoke?.displayName.orEmpty(), invoke?.photoUrl)
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                "My Tasks",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Navy
            )
        }
        //task Type View
        item {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(vertical = 12.dp)
                ) {
                    TaskCategoryCard(
                        TaskType.Completed.type,
                        completedTask.value?.first()?.tasks?.size.toString().plus("Task"),
                        Color(0xFF7DC8E7),
                        height = 220.dp,
                        onClick = {
                            navController.navigate("${Screens.MainApp.TaskByCategory.route}/${TaskType.Completed.type}")

                        },
                        image = {
                            Image(
                                painter = painterResource(id = R.drawable.imac),
                                contentDescription = "",
                                modifier = Modifier.size(80.dp)
                            )

                        })
                    TaskCategoryCard(
                        TaskType.Pending.type,
                        pendingTask.value?.first()?.tasks?.size.toString().plus("Task"),
                        Color(0xFF7D88E7),
                        height = 190.dp,
                        onClick = {
                            navController.navigate("${Screens.MainApp.TaskByCategory.route}/${TaskType.Pending.type}")
                        },
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
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(vertical = 12.dp)
                ) {
                    TaskCategoryCard(
                        TaskType.Cancelled.type,
                        cancelledTask.value?.first()?.tasks?.size.toString().plus("Task"),
                        Color(0xFFE77D7D),
                        height = 190.dp,
                        onClick = {
                            navController.navigate("${Screens.MainApp.TaskByCategory.route}/${TaskType.Cancelled.type}")
                        },
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
                        onGoingTask.value?.first()?.tasks?.size.toString().plus("Task"),
                        Color(0xFF81E89E),
                        height = 220.dp,
                        onClick = {
                            navController.navigate("${Screens.MainApp.TaskByCategory.route}/${TaskType.OnGoing.type}")

                        },
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
        //today task view
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween

            ) {

                Text(
                    "Today Tasks",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Navy
                )
                Text(
                    "View all",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            navController.navigate(Screens.MainApp.TaskByDate.route)
                        },
                    fontSize = 12.sp,
                    color = PrimaryColor
                )
            }
        }
        items(tasksList.value) {
            TaskCard(taskTitle = it.task.title, timeFrom = it.task.timeFrom, timeTo = it.task.timeTo, tag = it.tags, it.task, viewModel)
        }
    }
}


@Composable
fun HeaderView(userName: String, photo: Uri?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween

    ) {

        Column {
            Text(
                "Hi, $userName",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Navy
            )
            Text(
                "Let's make this day productive",
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 14.sp,
                color = Color.DarkGray
            )
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
            //todo use coil
            if (photo.toString().isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.user_avatar_male),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = photo,
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}