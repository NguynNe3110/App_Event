package com.uzuu.managerevent.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.managerevent.data.local.dao.CategoriesDao
import com.uzuu.managerevent.data.local.dao.EventsDao
import com.uzuu.managerevent.data.local.dao.ImagesDao
import com.uzuu.managerevent.data.local.dao.TicketTypesDao
import com.uzuu.managerevent.data.local.dao.UsersDao
import com.uzuu.managerevent.data.local.entity.CategoriesEntity
import com.uzuu.managerevent.data.local.entity.EventsEntity
import com.uzuu.managerevent.data.local.entity.UsersEntity
import com.uzuu.managerevent.data.local.entity.ImagesEntity
import com.uzuu.managerevent.data.local.entity.TicketTypesEntity

@Database(
    entities = [
        UsersEntity::class,
        EventsEntity::class,
        ImagesEntity::class,
        TicketTypesEntity::class,
        CategoriesEntity::class
        ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UsersDao
    abstract fun eventDao() : EventsDao
    abstract fun imageDao() : ImagesDao
    abstract fun TicketTypesDao() : TicketTypesDao
    abstract fun categoryDao() : CategoriesDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "managerEvent.db"
                )
                    .build()
                INSTANCE =  db
                db
            }
        }
    }
}