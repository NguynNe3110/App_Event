package com.uzuu.learn19_4_practice_nav_isolation.feature.main

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
import com.uzuu.learn19_4_practice_nav_isolation.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.util.zip.Inflater

class HomeFragment : Fragment(){
    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).productRepository
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.homeState.collect { homeState ->
                        if(!homeState.error.toString().isEmpty()) {
                            binding.textView.text = "This is home!"
                        } else binding.textView.text = homeState.error

                        val enable = !homeState.isLoading
                        binding.btnAddHome.isEnabled = enable
                        binding.btnViewListHome.isEnabled = enable
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.homeEvent.collect { event->
                        when(event) {
                            is HomeUiEvent.Toast -> {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }

                            is HomeUiEvent.NavigateToList -> {
                                val action = HomeFragmentDirections.actionHomeToList()
                                findNavController().navigate(action)
                            }
                            is HomeUiEvent.clearInput -> {
                                binding.edtNameHome.setText("")
                                binding.edtDescriptionHome.setText("")
                                binding.edtPriceHome.setText("")
                            }
                        }
                    }
                }
            }
        }

        binding.btnAddHome.setOnClickListener {
            homeViewModel.onAdditional(
                binding.edtNameHome.text.toString().trim(),
                binding.edtDescriptionHome.text.toString().trim(),
                binding.edtPriceHome.text.toString().toLongOrNull() ?: 0
            )
        }

        binding.btnViewListHome.setOnClickListener {
            homeViewModel.onClickViewList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}