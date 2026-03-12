package com.uzuu.learn20_2_recycler_dao.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.uzuu.learn20_2_recycler_dao.data.local.dao.UserDao
import com.uzuu.learn20_2_recycler_dao.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object {
        @Volatile private var INSTANCE : AppDatabase ?= null

        fun get(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learn14_1.db"
                )
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}