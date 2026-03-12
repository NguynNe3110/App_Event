package com.uzuu.learn19_5_bottomnav_basic.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.uzuu.learn19_5_bottomnav_basic.R
import com.uzuu.learn19_5_bottomnav_basic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    lateinit var db : AppDatabase

//    lateinit var productRepository: ProductRepository
//    lateinit var timeRepository: TimeRepository

    lateinit var container : AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        container = AppContainer(applicationContext)

        //
        // khoi tao nav controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }

//    private fun initDb() {
//        db = AppDatabase.get(this)
//        val local = ProductLocalDataSource(db.productDao())
//        productRepository = ProductRepositoryImpl(local)
//
//        timeRepository = TimeRepositoryImpl(db.timeDao())
//
//    }
}