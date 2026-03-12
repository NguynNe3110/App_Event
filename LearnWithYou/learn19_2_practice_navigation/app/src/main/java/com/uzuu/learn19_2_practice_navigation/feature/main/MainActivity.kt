package com.uzuu.learn19_2_practice_navigation.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn19_2_practice_navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
