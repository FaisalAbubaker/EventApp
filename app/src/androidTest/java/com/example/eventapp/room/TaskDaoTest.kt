package com.example.eventapp.room

import androidx.test.filters.SmallTest
import com.example.eventapp.data.dao.TaskDao
import com.example.eventapp.data.database.EventsDatabase
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskType
import com.example.eventapp.data.entity.TaskWithTagLists
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class TaskDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: EventsDatabase

    private lateinit var taskDao: TaskDao

    val task = Task(
        taskId = 1,
        title = "title",
        description = "description",
        date = java.time.LocalDate.now().toString(),
        taskType = TaskType.Cancelled.type,
        timeFrom = "10:00",
        timeTo = "10:30",
        tagName = "Work"
    )

    @Before
    fun setUp(){
        hiltRule.inject()
        taskDao = database.taskDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertTask() = runTest {
        taskDao.addTask(task)
        val allTasks = taskDao.getAllTasks().first()
        assert(allTasks.contains(task))
    }

    @Test
    fun deleteTask() = runTest {
        taskDao.addTask(task)
        taskDao.deleteTask(task)
        val allTasks = taskDao.getAllTasks().first()
        assert(!allTasks.contains(task))
    }

    @Test
    fun getAllTasks() = runTest {
        val task = Task(
            taskId = 1,
            title = "title",
            description = "description",
            date = java.time.LocalDate.now().toString(),
            taskType = TaskType.Cancelled.type,
            timeFrom = "10:00",
            timeTo = "10:30",
            tagName = "Work"
        )
        val task2 = Task(
            taskId = 2,
            title = "title2",
            description = "description2",
            date = java.time.LocalDate.now().toString(),
            taskType = TaskType.Cancelled.type,
            timeFrom = "10:20",
            timeTo = "10:50",
            tagName = "Work"
        )
        taskDao.addTask(task)
        taskDao.addTask(task2)
        val allTasks = taskDao.getAllTasks().first()
        assert(allTasks == listOf(task,task2))
    }

    @Test
    fun upsertTag() = runTest {
        val tag = Tags(
            "Personal",
            "color"
        )
        taskDao.upsertTag(tag)
        val allTags = taskDao.getAllTags().first()
        assert(allTags.contains(tag))
    }

    @Test
    fun deleteTag() = runTest {
        val tag = Tags(
            "Personal",
            "color"
        )
        taskDao.upsertTag(tag)
        taskDao.deleteTag(tag)
        val allTags = taskDao.getAllTags().first()
        assert(!allTags.contains(tag))
        assert(allTags.isEmpty())
    }

    @Test
    fun getAllTags() = runTest {
        val tag = Tags(
            "Personal",
            "color"
        )
        val tag2 = Tags(
            "Work",
            "color"
        )
        taskDao.upsertTag(tag)
        taskDao.upsertTag(tag2)
        val allTags = taskDao.getAllTags().first()
        assert(allTags == listOf(tag,tag2))
    }

    @Test
    fun getTagsWithTask() = runTest {
        val tag = Tags(
            "Personal",
            "color"
        )
        val tag2 = Tags(
            "Work",
            "color"
        )
        val task = Task(
            taskId = 1,
            title = "title",
            description = "description",
            date = java.time.LocalDate.now().toString(),
            taskType = TaskType.Cancelled.type,
            timeFrom = "10:00",
            timeTo = "10:30",
            tagName = "Work"
        )
        val task2 = Task(
            taskId = 2,
            title = "title2",
            description = "description2",
            date = java.time.LocalDate.now().toString(),
            taskType = TaskType.Cancelled.type,
            timeFrom = "10:20",
            timeTo = "10:50",
            tagName = "Work"
        )
        taskDao.upsertTag(tag)
        taskDao.upsertTag(tag2)
        taskDao.addTask(task)
        taskDao.addTask(task2)
        val getTagsWithTask = taskDao.getTagsWithTask("Work").first()
        val expected = listOf(TaskWithTagLists(tag2, listOf(task,task2)))
        assert(getTagsWithTask == expected)
    }

}