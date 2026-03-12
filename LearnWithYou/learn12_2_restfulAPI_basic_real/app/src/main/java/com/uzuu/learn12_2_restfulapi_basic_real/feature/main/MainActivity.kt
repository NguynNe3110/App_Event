package com.uzuu.learn12_2_restfulapi_basic_real.feature.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn12_2_restfulapi_basic_real.R
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.AppDatabase
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.datasource.BookLocalDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.datasource.UserLocalDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.RetrofitProvider.bookApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.RetrofitProvider.categoryApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.RetrofitProvider.userApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource.BookRemoteDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource.CategoryDataRemoteSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource.UserRemoteDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.repository.BookRepositoryImpl
import com.uzuu.learn12_2_restfulapi_basic_real.data.repository.CategoryRepositoryImpl
import com.uzuu.learn12_2_restfulapi_basic_real.data.repository.UserRepositoryImpl
import com.uzuu.learn12_2_restfulapi_basic_real.databinding.ActivityMainBinding
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Categories
import com.uzuu.learn12_2_restfulapi_basic_real.ui.adapter.UserBookAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: UserBookAdapter

    private val viewModel: MainViewModel by viewModels {
        //get db
        val db = AppDatabase.get(applicationContext)

        //khoi tao local & remote
        val userLocal = UserLocalDataSource(userDao = db.userDao())
        val bookLocal = BookLocalDataSource(booksDao = db.bookDao())
        val userRemote = UserRemoteDataSource(userApi = userApi)
        val bookRemote = BookRemoteDataSource(bookApi = bookApi)
        val cateRemote = CategoryDataRemoteSource(categoryApi = categoryApi)
        //create repo
        val userRepo = UserRepositoryImpl(userLocal, userRemote)
        val bookRepo = BookRepositoryImpl(bookLocal, bookRemote)
        val cateRepo = CategoryRepositoryImpl(db.categoryDao(), remote = cateRemote)

        //create provider factory
        MainViewModelFactory(
            userRepo,
            bookRepo,
            cateRepo
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListView()
        setEventButton()
        observeState()
        observeEvent()
    }


    private fun setupListView() {
        adapter = UserBookAdapter(
            onItemClick = { item ->
                when(item) {
                    is UiItem.UserItem -> {
                        Toast.makeText(this,"Click user ${item.id}",Toast.LENGTH_SHORT).show()
                    }
                    is UiItem.BookItem -> {
                        Toast.makeText(this,"Click book ${item.id}",Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onMenuClick = { item, view ->
                val popup = PopupMenu(this, view)
                popup.menuInflater.inflate(R.menu.item_menu, popup.menu)
//                popup.setOnMenuItemClickListener {
//                    when(it.itemId) {
//                        R.id.
//                    }
//                }

                popup.show()
            }
        )

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        binding.recycler.setHasFixedSize(true)
    }

    private fun setupSpinner(cates: List<Categories>) {
        // setup dropdown
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cates
        )

        // layout khi mo ra
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinnerCategory.adapter = adapter

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = cates[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }
    private fun setEventButton() {
        binding.btncreateBook.setOnClickListener {

            val cate = binding.spinnerCategory.selectedItem as Categories

            viewModel.createBook(
                name = binding.bookname.text.toString().trim(),
                quantity = binding.quantity.text.toString().trim(),
                idCat = cate.id.toString()
            )
        }

        binding.btnupdateBook.setOnClickListener {

            val cate = binding.spinnerCategory.selectedItem as Categories

            viewModel.updateBook(
                id = binding.idBook.text.toString().trim(),
                name = binding.bookname.text.toString().trim(),
                quantity = binding.quantity.text.toString().trim(),
                idCat = cate.id.toString()
            )
        }

        binding.btndeleteBook.setOnClickListener {
            viewModel.deleteBook(
                id = binding.idBook.text.toString().trim()
            )
        }
        binding.btndeleteAllBook.setOnClickListener {
            viewModel.deleteAllBook()
        }
        binding.btnsyncBook.setOnClickListener {
            viewModel.syncBook()
        }

        binding.btncreateUser.setOnClickListener {
            viewModel.createUser(
                id = binding.idUser.text.toString().trim(),
                username = binding.username.text.toString().trim(),
                password = binding.password.text.toString().trim(),
                fullName = binding.fullname.text.toString().trim()
            )
        }
        binding.btnupdateUser.setOnClickListener {
            viewModel.updateUser(
                id = binding.idUser.text.toString().trim(),
                username = binding.username.text.toString().trim(),
                password = binding.password.text.toString().trim(),
                fullName = binding.fullname.text.toString().trim()
            )
        }
        binding.btndeleteUser.setOnClickListener {
            viewModel.deleteUser(
                id = binding.idUser.text.toString().trim(),
                username = binding.username.text.toString().trim()
            )
        }
        binding.btnDeleteAllUser.setOnClickListener {
            viewModel.deleteAllUser()
        }
        binding.btnsyncUser.setOnClickListener {
            viewModel.syncUser()
        }
        binding.btnInsertCategory.setOnClickListener { viewModel.InsertSampleCategory() }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect { state ->
                    binding.progress.visibility = if(state.isLoading) View.VISIBLE else View.GONE

                    val enabled = !state.isLoading
                    binding.btncreateUser.isEnabled = enabled
                    binding.btnupdateUser.isEnabled = enabled
                    binding.btndeleteUser.isEnabled = enabled
                    binding.btnDeleteAllUser.isEnabled = enabled
                    binding.btnsyncUser.isEnabled = enabled

                    binding.btncreateBook.isEnabled = enabled
                    binding.btnupdateBook.isEnabled = enabled
                    binding.btndeleteBook.isEnabled = enabled
                    binding.btndeleteAllBook.isEnabled = enabled
                    binding.btnsyncBook.isEnabled = enabled

                    println("DEBUG items size = ${state.items.size}")

                    adapter.submitList(state.items)

                    println("DEBUG du lieu category trong mainActivity size: ${state.categories.size} ")
                    if(state.categories.isNotEmpty() && binding.spinnerCategory.adapter == null){
                        setupSpinner(state.categories)
                    }
                }
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiEvent.collect { e ->
                    when(e) {
                        is MainUiEvent.Toast -> {
                            Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

