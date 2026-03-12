package com.uzuu.learn20_2_recycler_dao.feature.main

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn20_2_recycler_dao.R
import com.uzuu.learn20_2_recycler_dao.data.local.AppDatabase
import com.uzuu.learn20_2_recycler_dao.data.repository.UserRepositoryImpl
import com.uzuu.learn20_2_recycler_dao.databinding.ActivityMainBinding
import com.uzuu.learn20_2_recycler_dao.domain.model.User
import com.uzuu.learn20_2_recycler_dao.ui.adapter.UserAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.get(this)
        val repo = UserRepositoryImpl(
            userDao = db.userDao()
        )
        viewModel = MainViewModel(repo)

        setEventButton()
        setupRecycler()
        observeState()
        observeEvent()
    }

    fun setupRecycler(){
        adapter = UserAdapter { user, anchorView ->
            //todo code
            viewModel.onUserClick(user)
            showPopup(user, anchorView)
            //khi user cllick thi truyen ve viewModel de update state
        }

        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        binding.rvStudents.adapter = adapter
        binding.rvStudents.setHasFixedSize(true)

    }
    fun setEventButton() {
        binding.btnAddition.setOnClickListener {
            viewModel.insert(
                strId = binding.edtStudentCode.text.toString().trim(),
                strName = binding.edtStudentName.text.toString().trim()
            )
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.update(
                strId = binding.edtStudentCode.text.toString().trim(),
                strName = binding.edtStudentName.text.toString().trim()
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteUserById(strId = binding.edtStudentCode.text.toString().trim())
        }

        binding.btnClear.setOnClickListener {
            viewModel.deleteAll()
        }
    }


    private fun showPopup(user: User, view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.user_menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_delete -> {
                    viewModel.deleteUserById(user.id.toString())
                    true
                }
                R.id.menu_Fill -> {
                    viewModel.FillData(user)
                    true
                }
                else -> false
            }
        }

//        popup.setOnMenuItemClickListener { item ->
//            if (item.itemId == R.id.menu_delete) {
//                viewModel.deleteUserById(user.id)
//                true
//            } else false
//        }

        popup.show()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect { state ->
                    binding.progressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE

                    val enabled = !state.isLoading
                    binding.btnAddition.isEnabled = enabled
                    binding.btnClear.isEnabled = enabled
                    binding.btnUpdate.isEnabled = enabled
                    binding.btnDelete.isEnabled = enabled

                    adapter.submit(state.users)

                    binding.StudentCode.text = state.userSelected?.id.toString()
                    binding.StudentName.text = state.userSelected?.displayName.toString()
                }
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { e->
                    when(e) {
                        is MainUiEvent.Toast -> {
                            Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                        is MainUiEvent.FillData -> {
                            binding.edtStudentCode.setText(e.id.toString())
                            binding.edtStudentName.setText(e.name)
                        }
                    }
                }
            }
        }
    }
}