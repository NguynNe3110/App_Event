package com.uzuu.learn19_4_practice_nav_isolation.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.learn19_4_practice_nav_isolation.data.local.dao.ProductDao
import com.uzuu.learn19_4_practice_nav_isolation.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao() : ProductDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase ?= null

        fun get(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learn19_4.db"
                )
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}