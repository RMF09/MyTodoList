package com.rmf.mytodolist.di

import android.app.Application
import androidx.room.Room
import com.rmf.mytodolist.data.local.TaskDB
import com.rmf.mytodolist.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): TaskDB =
        Room.databaseBuilder(app, TaskDB::class.java, "task_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAccountDao(db: TaskDB): TaskDao = db.taskDao()
}