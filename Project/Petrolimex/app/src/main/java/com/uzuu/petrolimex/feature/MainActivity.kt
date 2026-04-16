package com.uzuu.petrolimex.feature

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
import com.uzuu.petrolimex.R
import com.uzuu.petrolimex.databinding.ActivityMainBinding
import com.uzuu.petrolimex.feature.main.MainUiEvent
import com.uzuu.petrolimex.feature.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEvent()
        observeUi()
        observerEvent()
    }

    fun observeUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                lifecycleScope.launch {
                    viewModel.uiState.collect { list ->
                        println("DEBUG: Collect list size = ${list.fuels.size}")

                        val dataFuel = list.fuels
                        if (dataFuel.size >= 3) {
                            val f1 = dataFuel[0]
                            val f2 = dataFuel[1]
                            val f3 = dataFuel[2]

                            binding.txtRon95vPrice1.text = f1.price1
                            binding.txtRon95vPrice2.text = f1.price2

                            binding.txtRon95iiiPrice1.text = f2.price1
                            binding.txtRon95iiiPrice2.text = f2.price2

                            binding.txtRon92Price1.text = f3.price1
                            binding.txtRon92Price2.text = f3.price2
                        }

                        val time = list.time
                        binding.time.text = time.time
                    }
                }
            }
        }
    }

    fun observerEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is MainUiEvent.Loading -> {
                            println("DEBUG: Sync success")
                            binding.btnSync.isEnabled = false
                            binding.progress.visibility = View.VISIBLE
                            binding.btnSync.text = ""
                        }

                        is MainUiEvent.Success -> {
                            println("DEBUG: Sync error")
                            binding.btnSync.isEnabled = true
                            binding.progress.visibility = View.GONE
                            binding.btnSync.text = "Sync dữ liệu"
                        }

                        is MainUiEvent.Error -> {
                            println("DEBUG: Sync error")
                            binding.btnSync.isEnabled = true
                            binding.progress.visibility = View.GONE
                            binding.btnSync.text = "Ôi thôi xong"
                        }

                        is MainUiEvent.Toast -> {
                            Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
    }
    fun setEvent(){
        binding.btnSync.setOnClickListener {
            println("DEBUG: Button clicked")
            viewModel.syncData()
        }
    }
}