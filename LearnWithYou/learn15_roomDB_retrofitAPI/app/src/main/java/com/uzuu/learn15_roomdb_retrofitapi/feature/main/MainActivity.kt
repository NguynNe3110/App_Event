package com.uzuu.learn15_roomdb_retrofitapi.feature.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn15_roomdb_retrofitapi.data.repository.UserRepositoryImpl
import com.uzuu.learn15_roomdb_retrofitapi.data.local.AppDatabase
import com.uzuu.learn15_roomdb_retrofitapi.data.remote.ApiClient
import com.uzuu.learn15_roomdb_retrofitapi.databinding.ActivityMainBinding
import com.uzuu.learn15_roomdb_retrofitapi.domain.repository.UserRepository
import com.uzuu.learn15_roomdb_retrofitapi.ui.UserAdapter
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel

    private val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init dependencies (bài 15 chưa dùng Hilt, nên tạo tay)
        val db = AppDatabase.get(this)
        val repo: UserRepository = UserRepositoryImpl(
            api = ApiClient.api,
            dao = db.userDao()
        )
        viewModel = UserViewModel(repo)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        // setup RecyclerView
        binding.recycler.adapter = adapter

        // refresh button
        binding.btnRefresh.setOnClickListener {
            viewModel.refresh()
        }

        binding.btnDeleteAll.setOnClickListener {
            viewModel.delete()
        }

        // observe uiState
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progress.visibility =
                            if (state.isLoading) View.VISIBLE else View.GONE

                        adapter.submit(state.users)

                        binding.tvStatus.text = when {
                            state.isLoading -> "Loading..."
                            state.error != null -> "Error: ${state.error}"
                            else -> "Loaded: ${state.users.size} users (from DB)"
                        }

                        state.error?.let { Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show() }
                    }
                }
            }
        }
    }
}
