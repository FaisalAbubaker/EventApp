package com.example.eventapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskWithTagLists
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Upsert
    suspend fun upsertTag(tag: Tags)

    @Delete
    suspend fun deleteTag(tag: Tags)

    @Query("SELECT * FROM tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Query("SELECT * FROM tags_table WHERE tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<List<TaskWithTagLists>>

}