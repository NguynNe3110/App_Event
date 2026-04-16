package com.uzuu.petrolimex.core.di

import android.content.Context
import androidx.room.Room
import com.uzuu.petrolimex.data.local.AppDatabase
import com.uzuu.petrolimex.data.local.dao.FuelDao
import com.uzuu.petrolimex.data.local.dao.TimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//khi 1 hàm được inject nhưng nó lại k biết tạo như nào thì phải dùng đến @module
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Provides
    fun provideTimeDao(db: AppDatabase): TimeDao {
        return db.timeDao()
    }

    @Provides
    fun provideFuelDao(db: AppDatabase): FuelDao {
        return db.fuelDao()
    }
}