package com.example.eventapp.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskType
import com.example.eventapp.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasks = taskRepository.getAllTasks()
    val tags = taskRepository.getAllTags()
    val tasksByTags = taskRepository.getTagsWithTask("Personal")

    init {
        viewModelScope.launch {
            taskRepository.insertTag(
                Tags(
                    "Work",
                    "Color"
                )
            )
            taskRepository.insertTag(
                Tags(
                    "Personal",
                    "Color"
                )
            )

            taskRepository.insertTask(
                Task(
                    title = "title",
                    description = "description",
                    date = "2022-02-02",
                    taskType = TaskType.OnGoing.type,
                    timeFrom = "10:20",
                    timeTo = "12:10",
                    tagName = "Work"
                )
            )

            taskRepository.insertTask(
                Task(
                    title = "title2",
                    description = "description2",
                    date = "2023-03-03",
                    taskType = TaskType.Pending.type,
                    timeFrom = "10:30",
                    timeTo = "12:30",
                    tagName = "Personal"
                )
            )
        }
    }
}