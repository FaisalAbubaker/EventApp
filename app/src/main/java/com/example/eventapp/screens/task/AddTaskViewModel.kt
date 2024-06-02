package com.example.eventapp.screens.task

//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.eventapp.repository.TaskRepository
//import com.example.eventapp.data.entity.Tags
//import com.example.eventapp.data.entity.Task
//import com.example.eventapp.data.entity.TaskTagCrossRef
//import com.example.eventapp.data.entity.TaskType
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class AddTaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
//
//    val title: MutableState<String?> = mutableStateOf("")
//    val description: MutableState<String?> = mutableStateOf("")
//    val taskDate: MutableState<String?> = mutableStateOf("")
//    val startTime: MutableState<String?> = mutableStateOf("")
//    val endTime: MutableState<String?> = mutableStateOf("")
//    private val taskType: MutableState<String?> = mutableStateOf(TaskType.OnGoing.type)
//    private val category: MutableState<String?> = mutableStateOf("")
//
//    //tag
//    val tagName: MutableState<String?> = mutableStateOf("")
//    val tagColor: MutableState<String> = mutableStateOf("")
//    val tagIcon: MutableState<String> = mutableStateOf("")
//
//    val allTags = taskRepository.getAllTags()
//
//    //selectedTags
//    val selectedTags = mutableStateOf<Set<Tags>>(emptySet())
//
//    fun addTask() {
//
//        viewModelScope.launch {
//            val task = Task(
//                title = title.value.orEmpty(),
//                description = description.value.orEmpty(),
//                date = taskDate.value.orEmpty(),
//                timeFrom = startTime.value.orEmpty(),
//                timeTo = endTime.value.orEmpty(),
//                taskType = taskType.value.orEmpty(),
//                tagName = category.value.orEmpty()
//            )
//            insertTaskWithTags(
//                task,
//                selectedTags.value.toList()
//            )
//        }
//    }
//
//    fun updateTask(id: Long) {
//
//        viewModelScope.launch {
//            val task = Task(
//                taskId = id,
//                title = title.value.orEmpty(),
//                description = description.value.orEmpty(),
//                date = taskDate.value.orEmpty(),
//                timeFrom = startTime.value.orEmpty(),
//                timeTo = endTime.value.orEmpty(),
//                taskType = taskType.value.orEmpty(),
//                tagName = category.value.orEmpty()
//            )
//            insertTaskWithTags(
//                task,
//                selectedTags.value.toList()
//            )
//        }
//    }
//
//    fun addTag() {
//        //todo add validation for tagName field
//        viewModelScope.launch {
//            taskRepository.insertTag(
//                Tags(
//                    tagName.value.orEmpty(),
//                    tagColor.value,
//                    tagIcon.value
//                )
//            )
//        }
//    }
//
//
//    fun getTaskFromId(id: Long){
//        viewModelScope.launch {
//            title.value = taskRepository.getTaskFromId(id).title
//            description.value = taskRepository.getTaskFromId(id).description
//            taskDate.value = taskRepository.getTaskFromId(id).date
//            startTime.value = taskRepository.getTaskFromId(id).timeFrom
//            endTime.value = taskRepository.getTaskFromId(id).timeTo
//            taskType.value = taskRepository.getTaskFromId(id).taskType
//            category.value = taskRepository.getTaskFromId(id).tagName
//
//        }
//    }
//
//    private suspend fun insertTaskWithTags(task: Task, tags: List<Tags>) {
//        val taskId = taskRepository.insertTask(task) // Insert the task and get its generated ID
//        taskRepository.insertTagList(tags)
//        val taskTagCrossRefs =
//            tags.map { TaskTagCrossRef(taskId, it.name) } // Create TaskTagCrossRef objects
//        taskRepository.insertTaskTagCrossRefs(taskTagCrossRefs) // Insert TaskTagCrossRef objects to associate tags with the task
//    }
//
//}