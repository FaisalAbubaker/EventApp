package com.example.eventapp.screens.task


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.eventapp.R
import com.example.eventapp.component.AddTagsListView
import com.example.eventapp.component.CustomTextField
import com.example.eventapp.component.TasksHeaderView
import com.example.eventapp.component.TimePickerDialog
import com.example.eventapp.navigation.Screens
import com.example.eventapp.ui.theme.PrimaryColor

@Composable
fun UpdateTaskScreen(
    navController: NavHostController,
    viewModel: TaskViewModel,
    taskId: Long?,
    navBackStackEntry: NavBackStackEntry
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getAllTag()
        if (taskId != null) {
            viewModel.getSelectedTask(taskId)
        }

    }
    if (navBackStackEntry.savedStateHandle.get<String>("selectedDate")?.isNotEmpty() == true) {
        viewModel.taskDate.value = navBackStackEntry.savedStateHandle.get<String>("selectedDate")!!
    }

    val allTags = viewModel.allTags.collectAsState()


    val showStartTimeTimeDialog = remember {
        mutableStateOf(false)
    }

    val showEndTimeTimeDialog = remember {
        mutableStateOf(false)
    }

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        item {
            //  header
            TasksHeaderView(stringResource(id = R.string.update_task)) {
                navController.popBackStack()
            }
        }
        //task fields
        item {
            CustomTextField(Modifier, stringResource(id = R.string.title), Color.Gray, viewModel.title)
            CustomTextField(
                Modifier,
                stringResource(id = R.string.date),
                Color.Gray,
                viewModel.taskDate,
                isReadOnly = true,
                trailingIcon = {
                    Icon(imageVector = Icons.Outlined.DateRange, "", modifier =
                    Modifier.clickable {
                        navController.navigate(Screens.MainApp.DateDialog.route)
                    })
                })
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomTextField(
                    Modifier
                        .weight(1f)
                        .clickable {
                            showStartTimeTimeDialog.value = true
                        },
                    stringResource(id = R.string.time_from), Color.Gray, viewModel.startTime, isReadOnly = true,
                )
                CustomTextField(
                    Modifier
                        .weight(1f)
                        .clickable {
                            showEndTimeTimeDialog.value = true
                        },
                    stringResource(id = R.string.time_to), Color.Gray, viewModel.endTime, isReadOnly = true,
                )
            }
            CustomTextField(Modifier, stringResource(id = R.string.description), Color.Gray, viewModel.description)

        }
        //tags List
        item {
            AddTagsListView(allTags, navController) {
                viewModel.selectedTags.value = it
                viewModel.selectedTags.value
            }
        }
        item {
            //update task button
            ButtonUpdateTask(viewModel, taskId, context, viewModel.title.value.isNotEmpty(),
                viewModel.taskDate.value.isNotEmpty(), viewModel.startTime.value.isNotEmpty(),
                viewModel.endTime.value.isNotEmpty(), viewModel.description.value.isNotEmpty())
        }
    }
    if (showStartTimeTimeDialog.value) {
        TimePickerDialog(
            onBackPress = {
                showStartTimeTimeDialog.value = false
            },
            onTimeSelected = { hour, minute ->
                viewModel.startTime.value = "$hour:$minute"
            }
        )
    }
    if (showEndTimeTimeDialog.value) {
        TimePickerDialog(
            onBackPress = {
                showEndTimeTimeDialog.value = false
            },
            onTimeSelected = { hour, minute ->
                viewModel.endTime.value = "$hour:$minute"
            }
        )
    }
}

@Composable
fun ButtonUpdateTask(addTask: TaskViewModel, taskId: Long?, context: Context,
                     title: Boolean,
                     date: Boolean,
                     timeF: Boolean,
                     timeT: Boolean,
                     desc: Boolean) {
    Button(
        onClick = {
            if (taskId != null && title && date && timeF && timeT && desc) {
                addTask.updateTask(taskId)
                Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
            .padding(bottom = 100.dp)
            .semantics {
                testTag = "Update Task Button"
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp), text = stringResource(id = R.string.update),
            fontSize = 16.sp,
            color = Color.White
        )
    }
}
