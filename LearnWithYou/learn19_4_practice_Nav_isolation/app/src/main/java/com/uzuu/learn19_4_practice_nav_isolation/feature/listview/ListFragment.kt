package com.uzuu.learn19_4_practice_nav_isolation.feature.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn19_4_practice_nav_isolation.databinding.FragmentListBinding
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.MainActivity
import com.uzuu.learn19_4_practice_nav_isolation.ui.adapter.ListAdapterBasic
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding?= null
    private val binding get() = _binding!!

    // khoi tao viewmodel
    private val ListViewModel: ListViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).productRepository
        ListVMFactory(repo)
    }

    private lateinit var adapter : ListAdapterBasic

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        setupButton()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    ListViewModel.listState.collect { listState ->
                        adapter.submitList(listState.products)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    ListViewModel.listEvent.collect { event ->
                        when(event) {
                            is ListUiEvent.Toast -> {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }

                            is ListUiEvent.NavigateToHome -> {
                                findNavController().popBackStack()
                            }

                            is ListUiEvent.NavigateToDetail -> {
                                val action = ListFragmentDirections
                                    .actionListToDetail(event.productId)

                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = ListAdapterBasic(
            onClick = { item ->
                ListViewModel.onClickItem(item.id)
                Toast.makeText(context, "Click product {$item}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.setHasFixedSize(true)
    }

    private fun setupButton() {
        binding.btnBackList.setOnClickListener {
            ListViewModel.onBackList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}