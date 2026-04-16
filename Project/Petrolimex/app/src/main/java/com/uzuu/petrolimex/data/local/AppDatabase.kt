package com.uzuu.petrolimex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uzuu.petrolimex.data.local.dao.FuelDao
import com.uzuu.petrolimex.data.local.dao.TimeDao
import com.uzuu.petrolimex.data.local.entity.FuelEntity
import com.uzuu.petrolimex.data.local.entity.TimeEntity


@Database(
    entities = [
        TimeEntity::class,
        FuelEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun timeDao(): TimeDao
    abstract fun fuelDao(): FuelDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context) :AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "petrolimex.db"
                )
                    .build()
                INSTANCE =  db
                db
            }
        }
    }
}


