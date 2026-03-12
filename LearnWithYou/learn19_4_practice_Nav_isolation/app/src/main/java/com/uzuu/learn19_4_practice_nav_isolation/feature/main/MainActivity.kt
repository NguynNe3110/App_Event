package com.uzuu.learn19_4_practice_nav_isolation.feature.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uzuu.learn19_4_practice_nav_isolation.R
import com.uzuu.learn19_4_practice_nav_isolation.data.local.AppDatabase
import com.uzuu.learn19_4_practice_nav_isolation.data.local.datasource.ProductLocalDataSource
import com.uzuu.learn19_4_practice_nav_isolation.data.repository.ProductRepositoryImpl
import com.uzuu.learn19_4_practice_nav_isolation.databinding.ActivityMainBinding
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    lateinit var database: AppDatabase
    lateinit var productRepository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.get(this)
        val local = ProductLocalDataSource(database.productDao())
        productRepository = ProductRepositoryImpl(local)
    }
}