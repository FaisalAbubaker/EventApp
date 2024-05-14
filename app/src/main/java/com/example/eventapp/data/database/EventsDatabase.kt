package com.example.eventapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eventapp.data.dao.TaskDao
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task


@Database(entities = [Task::class, Tags::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: EventsDatabase? = null

        fun getDatabase(context: Context): EventsDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "events_database"
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}