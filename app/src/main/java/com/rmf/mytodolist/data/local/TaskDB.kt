package com.rmf.mytodolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 2)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}