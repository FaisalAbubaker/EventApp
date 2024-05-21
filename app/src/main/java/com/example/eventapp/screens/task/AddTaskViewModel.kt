package com.example.eventapp.screens.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventapp.repository.TaskRepository
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskTagCrossRef
import com.example.eventapp.data.entity.TaskType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val taskDate: MutableState<String> = mutableStateOf("")
    val startTime: MutableState<String> = mutableStateOf("")
    val endTime: MutableState<String> = mutableStateOf("")
    val taskID: MutableState<Long> = mutableLongStateOf(0)
    private val taskType: MutableState<String> = mutableStateOf(TaskType.OnGoing.type)
    private val category: MutableState<String> = mutableStateOf("")

    //tag
    val tagName: MutableState<String> = mutableStateOf("")
    val tagColor: MutableState<String> = mutableStateOf("")
    val tagIcon: MutableState<String> = mutableStateOf("")

    val allTags = taskRepository.getAllTags()

    //selectedTags
    val selectedTags = mutableStateOf<Set<Tags>>(emptySet())

    fun addTask() {

        viewModelScope.launch {
            val task = Task(
                title = title.value,
                description = description.value,
                date = taskDate.value,
                timeFrom = startTime.value,
                timeTo = endTime.value,
                taskType = taskType.value,
                tagName = category.value
            )
            insertTaskWithTags(
                task,
                selectedTags.value.toList()
            )
        }
    }

    fun updateTask() {

        viewModelScope.launch {
            val task = Task(
                title = title.value,
                description = description.value,
                date = taskDate.value,
                timeFrom = startTime.value,
                timeTo = endTime.value,
                taskType = taskType.value,
                tagName = category.value
            )
            updateTaskWithTags(
                task,
                selectedTags.value.toList()
            )
        }
    }

    fun addTag() {
        //todo add validation for tagName field
        viewModelScope.launch {
            taskRepository.insertTag(
                Tags(
                    tagName.value,
                    tagColor.value,
                    tagIcon.value
                )
            )
        }
    }

    fun getTaskId(task: Task){
        viewModelScope.launch {
            taskRepository.getId(task.taskId!!)
        }
    }

    private suspend fun insertTaskWithTags(task: Task, tags: List<Tags>) {
        val taskId = taskRepository.insertTask(task) // Insert the task and get its generated ID
        val taskTagCrossRefs =
            tags.map { TaskTagCrossRef(taskId, it.name) } // Create TaskTagCrossRef objects
        taskRepository.insertTaskTagCrossRefs(taskTagCrossRefs) // Insert TaskTagCrossRef objects to associate tags with the task
    }

    private suspend fun updateTaskWithTags(task: Task, tags: List<Tags>) {
        val taskId = taskRepository.getId(task.taskId!!) // Insert the task and get its generated ID
        val taskTagCrossRefs =
            tags.map { TaskTagCrossRef(taskId, it.name) } // Create TaskTagCrossRef objects
        taskRepository.insertTaskTagCrossRefs(taskTagCrossRefs) // Insert TaskTagCrossRef objects to associate tags with the task
    }
}