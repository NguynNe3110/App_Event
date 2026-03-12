package com.uzuu.learn15_roomdb_retrofitapi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.learn15_roomdb_retrofitapi.data.local.entity.UserEntity
import com.uzuu.learn15_roomdb_retrofitapi.data.local.dao.UserDao


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}