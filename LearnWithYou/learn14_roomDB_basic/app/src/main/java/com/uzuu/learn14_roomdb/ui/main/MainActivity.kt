package com.uzuu.learn14_roomdb.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.uzuu.learn14_roomdb.databinding.ActivityMainBinding
import com.uzuu.learn14_roomdb.data.UserRepository
import com.uzuu.learn14_roomdb.data.local.AppDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        // 1) Build Room DB
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "learn14.db"
        ).build()

        // 2) Build Repo
        val repo = UserRepository(db.userDao())

        // 3) Factory
        MainViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSeed.setOnClickListener { viewModel.onSeedClick() }
        binding.btnClear.setOnClickListener { viewModel.onClearClick() }

        lifecycleScope.launch {
            launch {

            viewModel.uiState.collectLatest { state ->
                binding.tvStatus.text = when {
                    state.isLoading -> "Loading..."
                    state.error != null -> "Error: ${state.error}"
                    else -> "Loaded: ${state.users.size} users"
                }

                binding.tvUsers.text =
                    if (state.users.isEmpty()) "(empty)"
                    else state.users.joinToString("\n") { "${it.id} - ${it.displayName}" }
            }
            }
            launch {
                viewModel.uiEvent.collect { e->
                    when(e){
                        is MainUiEvent.Toast ->
                            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
