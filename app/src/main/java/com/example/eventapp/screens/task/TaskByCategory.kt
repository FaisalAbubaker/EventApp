package com.example.eventapp.screens.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventapp.R
import com.example.eventapp.component.TaskCard
import com.example.eventapp.component.TasksHeaderView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksByCategory(tag: String?, navController: NavHostController,
                    viewModel: TaskViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getTasksByTagName(tag.orEmpty())
    }
    val results = viewModel.taskWithTags.value

    Column(modifier = Modifier.padding(5.dp)) {
        TasksHeaderView(tag.orEmpty()) {
            navController.popBackStack()
        }
        var query by rememberSaveable { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }
        SearchBar(query = query, onQueryChange = { query = it }, onSearch = {
            active = false
            viewModel.searchInTasksAndTags(query)
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
        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(results) {

                TaskCard(
                    taskId = it.task.taskId,
                    taskTitle = it.task.title,
                    timeFrom = it.task.timeFrom,
                    timeTo = it.task.timeTo,
                    tag = it.tags.filter { it.name == tag.orEmpty() },
                    task = it.task,
                    viewModel = viewModel,
                    navController
                )
            }
        }
    }
}