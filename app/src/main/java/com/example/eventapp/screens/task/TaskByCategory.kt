package com.example.eventapp.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventapp.component.TaskCard
import com.example.eventapp.component.TasksHeaderView
import com.example.eventapp.data.entity.TagWithTaskLists
import com.example.eventapp.data.entity.Task

@Composable
fun TasksByCategory(tagWithTaskLists: TagWithTaskLists?, navController: NavHostController,
                    viewModel: TaskViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        TasksHeaderView(tagWithTaskLists?.tag?.name.orEmpty()) {
            navController.popBackStack()
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tagWithTaskLists?.tasks.orEmpty()) {

                TaskCard(
                    taskTitle = it.title,
                    it.timeFrom,
                    timeTo = it.timeTo,
                    tag = listOf(tagWithTaskLists?.tag),
                    task = it,
                    viewModel = viewModel
                )
            }
        }
    }
}