package com.example.eventapp.screens.task

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventapp.R
import com.example.eventapp.component.CalendarWeeklyView
import com.example.eventapp.component.TaskCard
import com.example.eventapp.navigation.Screens
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskByDateScreen(viewmodel: TaskViewModel, navController: NavHostController) {
    val tasks = viewmodel.taskWithTags


    LaunchedEffect(Unit) {
        viewmodel.sortTasksByDate(LocalDate.now().toString())
    }
    var selectedDate by remember {
        mutableStateOf(LocalDate.now().toString())
    }
    Column {
        var query by rememberSaveable { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }
        SearchBar(query = query, onQueryChange = { query = it }, onSearch = {
            active = false
            viewmodel.searchInTasksAndTags(query)
        },
            active = active, onActiveChange = { active = it },
            placeholder = {
                Text(text = stringResource(id = R.string.search_for_task))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "",
                    modifier = Modifier.clickable {
                        if (query != "") {
                            query = ""
                        }
                    })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
        }
            CalendarWeeklyView(onDateSelected = {
                viewmodel.sortTasksByDate(it.toString())
                selectedDate = it.toString()
            })

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (selectedDate == LocalDate.now()
                            .toString()
                    ) stringResource(id = R.string.today) else selectedDate,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Timer()
            }
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val groupedList = tasks.value.groupBy { it.task.timeFrom }
                if (groupedList.isNotEmpty()) {
                    groupedList.forEach {
                        item {
                            Divider()
                        }
                        item {
                            LazyRow(
                                modifier = Modifier.fillParentMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                item {
                                    Text(text = it.key.orEmpty())
                                }
                                items(it.value) { taskWithTags ->
                                    Box(modifier = Modifier.fillParentMaxWidth(0.6f)) {
                                        TaskCard(
                                            taskId = taskWithTags.task.taskId,
                                            taskTitle = taskWithTags.task.title,
                                            taskWithTags.task.timeFrom,
                                            taskWithTags.task.timeTo,
                                            taskWithTags.tags,
                                            task = taskWithTags.task,
                                            viewModel = viewmodel,
                                            navController
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_empty_view),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = stringResource(id = R.string.no_tasks),
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }

            }
        }
    }

@Composable
private fun Timer() {
    var time by remember { mutableStateOf("") }
    val sdf = remember { SimpleDateFormat("HH:mm", Locale.ROOT) }
    LaunchedEffect(key1 = Unit){
        while(isActive){
            time = sdf.format(Date())
            delay(1000)
        }
    }
    Text(text = time)
}