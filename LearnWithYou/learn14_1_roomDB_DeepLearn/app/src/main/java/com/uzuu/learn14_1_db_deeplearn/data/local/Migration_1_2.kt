package com.uzuu.learn14_1_db_deeplearn.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// V1: users(id, name)
// V2: users(id, name, email)
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE users ADD COLUMN email TEXT NOT NULL DEFAULT ''")
    }
}