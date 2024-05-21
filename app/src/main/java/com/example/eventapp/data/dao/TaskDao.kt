package com.example.eventapp.data.dao

import androidx.privacysandbox.ads.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.eventapp.data.entity.TagWithTaskLists
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskTagCrossRef
import com.example.eventapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Upsert
    suspend fun addTask(task: Task): Long

    @Query("SELECT task_id FROM task_table WHERE task_id = :taskId")
    suspend fun getTaskId(taskId: Long): Long

    @Transaction
    @Upsert
    suspend fun insertTaskWithTags(task: Task, tags: List<Tags>)

    @Transaction
    @Upsert
    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>)

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

    @Transaction
    @Query("SELECT * FROM tags_table WHERE tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<List<TagWithTaskLists>>

//    @Query("SELECT * FROM tags_table WHERE tag_name = :tagName")
//    fun getTagsWithTask(tagName: String): Flow<List<TaskWithTagLists>>


    @Query("SELECT * FROM task_table WHERE date LIKE :date")
    fun sortByCreationDate(date: String): Flow< List<TaskWithTags>>

    @Upsert
    suspend fun upsertTagList(tag: List<Tags>)

    @Transaction
    @Query("SELECT * FROM task_table")
    fun getTaskWithTags(): Flow<List<TaskWithTags>>

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getTagWithTaskLists(): Flow<List<TagWithTaskLists>>


}