package com.example.eventapp.data.dao

import androidx.privacysandbox.ads.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.eventapp.data.entity.SearchResults
import com.example.eventapp.data.entity.TagWithTaskLists
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskTagCrossRef
import com.example.eventapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun addTask(task: Task): Long

    @Upsert
    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>)

    @Delete
    suspend fun deleteTask(task: Task)

    @Upsert//@Insert(onConflict = OnConflictStrategy.Replace)
    suspend fun upsertTag(tag: Tags)

    @Delete
    suspend fun deleteTag(tag: Tags)

    @Transaction
    @Query("SELECT * From tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Transaction
    @Query(" Select * From tags_table where tag_name = :tagName Limit 1")
    fun getTagsWithTask(tagName: String): Flow<TagWithTaskLists>

    @Query("SELECT * FROM task_table WHERE date LIKE :date")
    fun sortByCreationDate(date: String): Flow<List<TaskWithTags>>

    @Upsert
    suspend fun upsertTagList(tag: List<Tags>)

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getTagWithTaskLists(): Flow<List<TagWithTaskLists>>

    @Transaction
    @Query("SELECT * FROM task_table WHERE task_title LIKE '%' || :searchQuery || '%' OR task_description LIKE '%' || :searchQuery || '%'")
    fun searchTasksWithTags(searchQuery: String): List<TaskWithTags>

    @Transaction
    @Query("SELECT * FROM tags_table WHERE tag_name LIKE '%' || :searchQuery || '%'")
    fun searchTagsWithTasks(searchQuery: String): List<TagWithTaskLists>

    @Transaction
    suspend fun searchCombined(searchQuery: String): SearchResults {
        val taskResults = searchTasksWithTags(searchQuery)
        val tagResults = searchTagsWithTasks(searchQuery)
        return SearchResults(taskResults, tagResults)
    }

    //to get selected task
    @Transaction
    @Query("SELECT * FROM task_table WHERE task_Id = :taskId Limit 1")
    suspend fun getTaskWithTagsById(taskId: Long): TaskWithTags

    @Transaction
    @Query("SELECT * FROM task_table")
    fun getAllTaskWithTags(): Flow<List<TaskWithTags>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskTagCrossRef(crossRef: TaskTagCrossRef): Long

    @Query("DELETE FROM tasktagcrossref WHERE task_Id = :taskId")
    suspend fun deleteTaskTagCrossRefs(taskId: Long)

    @Transaction
    suspend fun updateTaskWithTags(task: Task, tags: List<Tags>) {
        // Update the task
        addTask(task)

        // Remove existing task-tag associations
        deleteTaskTagCrossRefs(task.taskId!!)

        // Insert tags and create new associations
        for (tag in tags) {
            upsertTag(tag)
            insertTaskTagCrossRef(TaskTagCrossRef(task.taskId!!, tag.name))
        }
    }

    @Transaction
    @Query("SELECT * FROM task_table")
    suspend fun getAllTasksWithTags(): List<TaskWithTags>
}