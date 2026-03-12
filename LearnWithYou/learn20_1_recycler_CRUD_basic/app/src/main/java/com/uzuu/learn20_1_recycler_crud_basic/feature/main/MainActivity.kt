package com.uzuu.learn20_1_recycler_crud_basic.feature.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn20_1_recycler_crud_basic.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        setupClicks()
        observeState()
        observeEvent()
    }

    private fun setupRecycler() {
        adapter = UserAdapter { user ->
            viewModel.onUserClick(user)
        }

        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        binding.rvStudents.adapter = adapter
        binding.rvStudents.setHasFixedSize(true)
    }

    private fun setupClicks() {
        binding.btnAddition.setOnClickListener {
            viewModel.onAdd(
                code = binding.edtStudentCode.text.toString(),
                name = binding.edtStudentName.text.toString()
            )
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.onUpdate(
                code = binding.edtStudentCode.text.toString(),
                name = binding.edtStudentName.text.toString()
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.onDelete()
        }

        binding.btnClear.setOnClickListener {
            viewModel.onClear()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    // disable nút khi loading
                    val enabled = !state.isLoading
                    binding.btnAddition.isEnabled = enabled
                    binding.btnUpdate.isEnabled = enabled
                    binding.btnDelete.isEnabled = enabled
                    binding.btnClear.isEnabled = enabled

                    adapter.submit(state.users)
                }
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is UserUiEvent.Toast -> {
                            Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is UserUiEvent.FillInputs -> {
                            binding.edtStudentCode.setText(event.code)
                            binding.edtStudentName.setText(event.name)
                        }

                        UserUiEvent.ClearInputs -> {
                            binding.edtStudentCode.setText("")
                            binding.edtStudentName.setText("")
                        }
                    }
                }
            }
        }
    }
}