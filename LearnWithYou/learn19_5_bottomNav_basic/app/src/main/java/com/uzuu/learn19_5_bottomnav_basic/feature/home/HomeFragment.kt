package com.uzuu.learn19_5_bottomnav_basic.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentHomeBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.main.AppContainer
import com.uzuu.learn19_5_bottomnav_basic.feature.main.MainActivity
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {
    private var _binding : FragmentHomeBinding?= null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).container.productRepository
        HomeVMFactory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButton()
        observeAll()
    }

    private fun setupButton() {
        binding.btnAddHome.setOnClickListener {
            homeViewModel.onClickAdd(
                binding.edtNameHome.text.toString().trim(),
                binding.edtDescriptionHome.text.toString().trim(),
                binding.edtPriceHome.text.toString().trim()
            )
        }
    }

    private fun observeAll() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.homeState.collect { homeState ->
                        if(!homeState.error.toString().isEmpty()) {
                            binding.textView.text = "This is home!"
                        } else binding.textView.text = homeState.error

                        binding.progress.visibility = if(homeState.idLoading) View.VISIBLE else View.GONE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.homeEvent.collect { event->
                        when(event) {
                            is HomeViewModel.HomeUiEvent.Toast -> {
                                Toast.makeText(context,event.message, Toast.LENGTH_SHORT).show()
                            }
                            is HomeViewModel.HomeUiEvent.ClearInput -> {
                                binding.edtNameHome.setText("")
                                binding.edtDescriptionHome.setText("")
                                binding.edtPriceHome.setText("")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}