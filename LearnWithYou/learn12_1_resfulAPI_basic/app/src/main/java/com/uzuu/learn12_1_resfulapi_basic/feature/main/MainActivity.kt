package com.uzuu.learn12_1_resfulapi_basic.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn12_1_resfulapi_basic.data.remote.RetrofitProvider
import com.uzuu.learn12_1_resfulapi_basic.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import com.uzuu.learn12_1_resfulapi_basic.domain.repository.MainRepository
import com.uzuu.learn12_1_resfulapi_basic.ui.adapter.SimpleItemAdapter

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SimpleItemAdapter

    //giống khởi tạo viewmodel đơn giản
    private val viewModel: MainViewModel by lazy {
        val repo = MainRepository(RetrofitProvider.jsonPlaceHolder)
        MainViewModel(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        setupClicks()
        observe()

        // auto load (mo la co)
        viewModel.getPosts()
    }

    private fun setupRecycler() {
        adapter = SimpleItemAdapter { item ->
            when (item) {
                is UiItem.PostItem -> Toast.makeText(this, "Click post id=${item.id}", Toast.LENGTH_SHORT).show()
                is UiItem.CommentItem -> Toast.makeText(this, "Click comment id=${item.id}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
    }

    private fun setupClicks() {
        binding.btnGetPosts.setOnClickListener {
            viewModel.getPosts()
        }

        binding.btnGetComments.setOnClickListener {
            // có thể truyền string qua viewmodel sau đó map bên đó
            val postId = binding.edtPostId.text.toString().toIntOrNull() ?: 1
            viewModel.getComments(postId)
        }

        binding.btnPost.setOnClickListener {
            val userId = binding.edtUserId.text.toString().toIntOrNull() ?: 1
            val title = binding.edtTitle.text.toString().ifBlank { "New title" }
            val body = binding.edtBody.text.toString().ifBlank { "New body" }
            viewModel.createPost(userId, title, body)
        }

        binding.btnPut.setOnClickListener {
            val id = binding.edtPostId.text.toString().toIntOrNull() ?: 1
            val userId = binding.edtUserId.text.toString().toIntOrNull() ?: 1
            val title = binding.edtTitle.text.toString().ifBlank { "Updated title" }
            val body = binding.edtBody.text.toString().ifBlank { "Updated body" }
            viewModel.updatePost(id, userId, title, body)
        }

        binding.btnDelete.setOnClickListener {
            val id = binding.edtPostId.text.toString().toIntOrNull() ?: 1
            viewModel.deletePost(id)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progress.visibility = if (state.isLoading) android.view.View.VISIBLE else android.view.View.GONE
                        binding.tvStatus.text = "Status: ${state.status}"
                        adapter.submitList(state.items)
                    }
                }
                launch {
                    viewModel.uiEvent.collect { e ->
                        when (e) {
                            is MainUiEvent.Toast ->
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}