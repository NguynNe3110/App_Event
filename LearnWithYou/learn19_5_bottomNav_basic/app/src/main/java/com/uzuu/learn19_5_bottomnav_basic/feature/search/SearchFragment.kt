package com.uzuu.learn19_5_bottomnav_basic.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentSearchBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.main.MainActivity
import com.uzuu.learn19_5_bottomnav_basic.feature.main.UiItem
import com.uzuu.learn19_5_bottomnav_basic.feature.profile.ProfileVMFactory
import com.uzuu.learn19_5_bottomnav_basic.feature.profile.ProfileViewModel
import com.uzuu.learn19_5_bottomnav_basic.ui.adapter.SearchAdapter
import kotlinx.coroutines.launch
import kotlin.getValue

class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding?= null
    private val binding get() = _binding!!


    private lateinit var adapter : SearchAdapter
    private val viewModel: SearchViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).container.productRepository
        SearchVMFactory(repo)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButton()
        setupAdapter()
        observeAll()
    }

    private fun setupButton() {
        binding.edtSearch.addTextChangedListener {
            viewModel.onSearchChange(it.toString())
        }
    }

    private fun observeAll() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchState.collect { state ->
                        // code state
                        adapter.submitList(
                            state.items.map { pro ->
                                UiItem(
                                    id = pro.id,
                                    name = pro.name,
                                    price = pro.price
                                )
                            }
                        )
                    }
                }
                launch {
                    viewModel.products.collect { list ->
                        adapter.submitList(list)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchEvent.collect { event ->
                        when(event) {
                            is SearchViewModel.SearchUiEvent.Toast -> {
                                Toast.makeText(context,"{${event.message}", Toast.LENGTH_SHORT)
                            }

                            is SearchViewModel.SearchUiEvent.NavigateToDetail -> {
                                val action = SearchFragmentDirections.actionSearchToDetail(event.id)
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = SearchAdapter(
            onClick = { item ->
                viewModel.onClickItem(item.id)
                Toast.makeText(context, "Click product {$item}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}