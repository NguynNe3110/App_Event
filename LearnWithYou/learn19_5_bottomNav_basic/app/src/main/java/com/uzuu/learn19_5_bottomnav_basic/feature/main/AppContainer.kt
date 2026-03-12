package com.uzuu.learn19_5_bottomnav_basic.feature.main

import android.content.Context
import com.uzuu.learn19_5_bottomnav_basic.data.local.AppDatabase
import com.uzuu.learn19_5_bottomnav_basic.data.local.datasource.ProductLocalDataSource
import com.uzuu.learn19_5_bottomnav_basic.data.repository.ProductRepositoryImpl
import com.uzuu.learn19_5_bottomnav_basic.data.repository.TimeRepositoryImpl

class AppContainer(context: Context) {

    private val db = AppDatabase.get(context)

    private val productLocal = ProductLocalDataSource(db.productDao())

    val productRepository = ProductRepositoryImpl(productLocal)
    val timeRepository = TimeRepositoryImpl(db.timeDao())

}