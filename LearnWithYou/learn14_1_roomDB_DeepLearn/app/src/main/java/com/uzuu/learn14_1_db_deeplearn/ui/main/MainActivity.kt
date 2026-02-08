package com.uzuu.learn14_1_db_deeplearn.ui.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.uzuu.learn14_1_db_deeplearn.data.local.AppDatabase
import com.uzuu.learn14_1_db_deeplearn.data.repository.DbRepository
import com.uzuu.learn14_1_db_deeplearn.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        val db = AppDatabase.get(applicationContext)
        val repo = DbRepository(db.userDao(), db.postDao())
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repo) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddUser.setOnClickListener {
            val name = binding.edtUserName.text.toString()
            val email = binding.edtUserEmail.text.toString()
            viewModel.addUser(name, email)
        }

        binding.btnAddPost.setOnClickListener {
            val userId = binding.edtUserId.text.toString().toLongOrNull() ?: -1
            val title = binding.edtPostTitle.text.toString()
            viewModel.addPost(userId, title)
        }

        binding.btnUpdateUser.setOnClickListener {
            val userId = binding.edtUserId.text.toString().toLongOrNull() ?: -1
            val name = binding.edtUserName.text.toString()
            val email = binding.edtUserEmail.text.toString()
            viewModel.updateUser(userId, name, email)
        }

        binding.btnDeleteUser.setOnClickListener {
            val userId = binding.edtUserId.text.toString().toLongOrNull() ?: -1
            viewModel.deleteUser(userId)
        }

        binding.btnClearAll.setOnClickListener {
            viewModel.clearAll()
        }

        // Observe uiState
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tvLog.text = state.logText
                        binding.tvUsers.text = state.usersText
                    }
                }
            }
        }
    }
}