package com.example.eventapp.repository

import com.example.eventapp.data.dao.TaskDao
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskWithTagLists
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    suspend fun insertTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    suspend fun insertTag(tag: Tags){
        taskDao.upsertTag(tag)
    }

    suspend fun deleteTag(tag: Tags){
        taskDao.deleteTag(tag)
    }

    fun getAllTags(): Flow<List<Tags>>{
        return taskDao.getAllTags()
    }

    fun getTagsWithTask(tagName: String): Flow<List<TaskWithTagLists>>{
        return taskDao.getTagsWithTask(tagName)
    }
}