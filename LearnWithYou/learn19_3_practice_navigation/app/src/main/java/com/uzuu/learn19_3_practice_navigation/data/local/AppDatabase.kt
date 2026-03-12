package com.uzuu.learn19_3_practice_navigation.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.uzuu.learn19_3_practice_navigation.data.local.dao.TaskDao
import com.uzuu.learn19_3_practice_navigation.data.local.entity.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "task_db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed data lần đầu cài app
                        // Note: cần tạo instance riêng rồi insert, làm trong coroutine
                    }
                })
                .build()
        }

        fun seedIfEmpty(database: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = database.taskDao()
                if (dao.count() == 0) {
                    dao.insertAll(
                        listOf(
                            TaskEntity(title = "Learn Navigation", done = false),
                            TaskEntity(title = "Learn Room", done = false),
                            TaskEntity(title = "Learn Retrofit", done = false)
                        )
                    )
                }
            }
        }
    }
}