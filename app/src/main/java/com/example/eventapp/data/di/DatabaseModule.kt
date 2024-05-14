package com.example.eventapp.data.di

import android.content.Context
import com.example.eventapp.data.database.EventsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): EventsDatabase {
        return EventsDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: EventsDatabase) = database.taskDao()

}