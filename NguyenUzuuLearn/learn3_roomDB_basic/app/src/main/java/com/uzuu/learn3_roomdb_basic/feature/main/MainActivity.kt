package com.uzuu.learn3_roomdb_basic.feature.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.room.Room
import com.uzuu.learn3_roomdb_basic.data.UserRepository
import com.uzuu.learn3_roomdb_basic.data.local.AppDatabase
import com.uzuu.learn3_roomdb_basic.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels{
        //build room db
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "learn3.db"
        ).build()

        //build repo
        val repo = UserRepository(db.userDao())

        //factory
        MainViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSeed.setOnClickListener { viewModel.onSeedClick() }
        binding.btnClear.setOnClickListener { viewModel.onClearClick() }
        binding.btnInsertOne.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            binding.editTextId.text.toString().toIntOrNull()?.let { id ->
                viewModel.onInsertClick(id, name)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {

                    viewModel.uiState.collect { state ->
                        //kiến trúc when k có biến
                        // giong if else nhung gon hon
                        binding.tvStatus.text = when {
                            // isloading = true thì hiển thị...
                            state.isLoading -> "Loading..."
                            // nếu co loi thi hien thi loi
                            state.error != null -> "Error: ${state.error}"
                            // case else, k load, k loi,
                            else -> "Loaded: ${state.users.size} users"
                        }

                        binding.tvUsers.text =
                            if (state.users.isEmpty()) "empty!"
                            else state.users.joinToString("\n") {
                                "${it.id} - ${it.displayName}"
                            }
                    }
                }
            }
        }
    }
}