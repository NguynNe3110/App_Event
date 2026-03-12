package com.uzuu.learn12_2_restfulapi_basic_real.data.local

import android.content.Context
import androidx.recyclerview.widget.ConcatAdapter
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao.BooksDao
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao.CategoriesDao
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao.UsersDao
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.BooksEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.CategoriesEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.UsersEntity


@Database(
    entities = [UsersEntity::class, BooksEntity::class, CategoriesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UsersDao
    abstract fun bookDao() : BooksDao
    abstract fun categoryDao() : CategoriesDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learn12_2.db"
                )
                    .build()
                INSTANCE =  db
                db
            }
        }
    }
}