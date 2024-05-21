package com.example.eventapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.eventapp.data.dao.TaskDao
import com.example.eventapp.data.entity.Tags
import com.example.eventapp.data.entity.Task
import com.example.eventapp.data.entity.TaskTagCrossRef


@Database(entities = [Task::class, Tags::class, TaskTagCrossRef::class], version = 3, exportSchema = false)
abstract class EventsDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: EventsDatabase? = null

        fun getDatabase(context: Context): EventsDatabase {

            val MIGRATION_1_2 = object : Migration(1,2){
                override fun migrate(db: SupportSQLiteDatabase){
                    db.execSQL("ALTER TABLE tags_table ADD COLUMN icon_name TEXT NOT NULL DEFAULT ''")
                }
            }

            val MIGRATION_2_3 = object : Migration(2,3){
                override fun migrate(db: SupportSQLiteDatabase){
                    db.execSQL("CREATE TABLE IF NOT EXISTS `TaskTagCrossRef` ("+
                    "`task_id` INTEGER NOT NULL, " +
                    "`tag_name` TEXT NOT NULL, " +
                    "PRIMARY KEY(`task_id`, `tag_name`) )")
                }
            }

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "events_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}