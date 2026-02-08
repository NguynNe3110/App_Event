package com.uzuu.learn14_1_roomdb_foreignkey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.dao.ClassDao
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.dao.UserDao
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.ClassEntity
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ClassEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun classDao() : ClassDao
}