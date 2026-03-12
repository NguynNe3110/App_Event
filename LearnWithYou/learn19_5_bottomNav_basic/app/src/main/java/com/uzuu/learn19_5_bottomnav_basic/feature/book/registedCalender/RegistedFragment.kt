package com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentRegisterBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.book.BookFragmentDirections
import com.uzuu.learn19_5_bottomnav_basic.feature.main.MainActivity
import com.uzuu.learn19_5_bottomnav_basic.ui.adapter.RegisterAdapter
import kotlinx.coroutines.launch

class RegistedFragment: Fragment() {

    private var _binding: FragmentRegisterBinding ?= null
    val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).container.timeRepository
        RegisterVMFactory(repo)
    }

    private lateinit var adapter : RegisterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.regisState.collect { state ->
                        println("DEBUG  state nhan ve la(in register fragment); $state")
                        adapter.submitList(state.itemTimes)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.regisEvent.collect { event ->
                        when(event) {
                            is RegisterViewModel.RegisterUiEvent.Toast -> {
                                Toast.makeText(context,event.mess, Toast.LENGTH_SHORT).show()
                            }

                            is RegisterViewModel.RegisterUiEvent.navigateToDetail -> {
                                val action = BookFragmentDirections.actionRegisterToDetailRegister(event.id)
                                println("DEBUG id truyen di ${event.id}")
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = RegisterAdapter{ item ->
            viewModel.onClickItem(item.id)
            Toast.makeText(context, "${item}", Toast.LENGTH_SHORT).show()
        }

        binding.recycerRegister.layoutManager = LinearLayoutManager(context)
        binding.recycerRegister.adapter = adapter
        binding.recycerRegister.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}