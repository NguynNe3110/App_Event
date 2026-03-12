package com.uzuu.learn4_roomdb_retrofitapi_basic.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.learn4_roomdb_retrofitapi_basic.data.local.dao.UserDao
import com.uzuu.learn4_roomdb_retrofitapi_basic.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object{

        @Volatile private var INSTANCE: AppDatabase?= null

        fun get(context: Context) : AppDatabase {
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