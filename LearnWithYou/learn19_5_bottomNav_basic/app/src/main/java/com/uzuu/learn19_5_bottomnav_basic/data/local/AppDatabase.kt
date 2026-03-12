package com.uzuu.learn19_5_bottomnav_basic.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.learn19_5_bottomnav_basic.data.local.dao.ProductDao
import com.uzuu.learn19_5_bottomnav_basic.data.local.dao.TimeDao
import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.ProductEntity
import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.TimeEntity

@Database(
    entities = [ProductEntity::class, TimeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao() : ProductDao
    abstract fun timeDao() : TimeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase ?= null

        fun get(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learn19_5.db"
                )
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}