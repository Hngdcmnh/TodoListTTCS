package com.example.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao():TaskDao
    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            val instance= INSTANCE
            if(instance!=null)
            {
                return instance
            }
            synchronized(this)
            {
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }

        fun isNull():Boolean
        {
            return (INSTANCE == null)
        }
    }

}