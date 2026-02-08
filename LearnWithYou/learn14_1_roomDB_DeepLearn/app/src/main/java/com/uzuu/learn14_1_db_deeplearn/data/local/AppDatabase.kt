package com.uzuu.learn14_1_db_deeplearn.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.learn14_1_db_deeplearn.data.local.dao.PostDao
import com.uzuu.learn14_1_db_deeplearn.data.local.dao.UserDao
import com.uzuu.learn14_1_db_deeplearn.data.local.entity.PostEntity
import com.uzuu.learn14_1_db_deeplearn.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learn14_1.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}