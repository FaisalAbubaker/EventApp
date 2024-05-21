package com.example.eventapp.screens.task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventapp.data.entity.TagWithTaskLists
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskType
import com.example.eventapp.data.entity.TaskWithTags
import com.example.eventapp.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasks = mutableStateOf<List<Task>>(emptyList())

    val tags = taskRepository.getAllTags()
    val cancelledTasks = taskRepository.getTagWithTasksList(TaskType.Cancelled.type)
    val pendingTasks = taskRepository.getTagWithTasksList(TaskType.Pending.type)
    val completedTasks = taskRepository.getTagWithTasksList(TaskType.Completed.type)
    val onGoingTasks = taskRepository.getTagWithTasksList(TaskType.OnGoing.type)

    val tagWithTasks = mutableStateOf<List<TagWithTaskLists>>(emptyList())
    val taskWithTags =  mutableStateOf<List<TaskWithTags>> (emptyList())

    init {
        //add base tags
        viewModelScope.launch {
            val tagsList = TaskType.entries.map {
                Tags(it.type, it.color, it.icon)
            }
            taskRepository.insertTagList(tagsList)
        }
        getTagWithTaskLists()

    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
    fun sortTasksByDate(date: String) {
        viewModelScope.launch {
            taskRepository.sortTasksByDate(date).collect {
                taskWithTags.value = it
            }
        }
    }

    private fun getTagWithTaskLists() {
        viewModelScope.launch {
            taskRepository.getTagWithTaskLists().collect{
                tagWithTasks.value =it
            }
        }
    }
}