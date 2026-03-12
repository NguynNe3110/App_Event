package com.uzuu.learn20_recycler_basic.feature.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn20_recycler_basic.R
import com.uzuu.learn20_recycler_basic.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        observeUi()
    }

    private fun setupRecycler(){
        // tạo instance của UserAdapter
        adapter = UserAdapter { User -> // {User -->...} là lambda } : khi click thì gọi hàm này
            Toast.makeText(this, "click: ${User.id} - ${User.displayName}", Toast.LENGTH_SHORT).show()
        }

        //layoutManager quản lý layout
        //LinearLayoutManager(this) xếp theo chiều dọc 1item / row per
        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        // dòng kết nối. Khi cần view, hãy hỏi adapter này
        binding.rvUsers.adapter = adapter

        // Kích thước RecyclerView không thay đổi khi dữ liệu thay đổi.
        binding.rvUsers.setHasFixedSize(true)
    }

    private fun observeUi(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tvStatus.text = when {
                            state.isLoading -> "Loading..."
                            state.error != null -> "Error: ${state.error}"
                            else -> "Loaded: ${state.users.size} users"
                        }

                        binding.progress.visibility = if(state.isLoading) View.VISIBLE else View.INVISIBLE
                        //render list
                        adapter.submit(state.users)
                    }
                }
            }
        }
    }
}