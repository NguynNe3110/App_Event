package com.uzuu.learn19_3_practice_navigation.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn19_3_practice_navigation.data.local.AppDatabase
import com.uzuu.learn19_3_practice_navigation.data.repository.TaskRepositoryImpl
import com.uzuu.learn19_3_practice_navigation.databinding.ActivityMainBinding
import com.uzuu.learn19_3_practice_navigation.domain.repository.TaskRepository
import com.uzuu.learn19_3_practice_navigation.feature.task.TaskDetailVMFactory
import com.uzuu.learn19_3_practice_navigation.feature.task.TaskEditVMFactory
import com.uzuu.learn19_3_practice_navigation.feature.task.TaskListVMFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Room DB
    private val db: AppDatabase by lazy { AppDatabase.create(this) }

    // Repo
    private val repo: TaskRepository by lazy { TaskRepositoryImpl(db.taskDao()) }

    // Factories cho từng screen (mỗi màn 1 VM)
    val listFactory by lazy { TaskListVMFactory(repo) }
    val detailFactory by lazy { TaskDetailVMFactory(repo) }
    val editFactory by lazy { TaskEditVMFactory(repo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // seed data lần đầu (nếu DB rỗng)
        AppDatabase.seedIfEmpty(db)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}