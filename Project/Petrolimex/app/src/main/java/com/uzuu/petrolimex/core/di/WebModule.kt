package com.uzuu.petrolimex.core.di

import com.uzuu.petrolimex.data.repository.WebRepository
import com.uzuu.petrolimex.domain.repository.WebRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WebModule {

    @Binds
    @Singleton
    abstract fun bindWebRepository(
        impl: WebRepositoryImpl
    ): WebRepository
}