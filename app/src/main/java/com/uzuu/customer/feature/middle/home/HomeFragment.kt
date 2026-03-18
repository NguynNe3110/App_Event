package com.uzuu.customer.feature.middle.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.customer.databinding.FragmentHomeBinding
import com.uzuu.customer.domain.model.CategoryItem
import com.uzuu.customer.ui.adapter.CategoryAdapter
import com.uzuu.customer.ui.adapter.EventAdapter
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null

    val binding get() = _binding!!

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var eventAdapter: EventAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupEvent()
        setupAdapter()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupEvent() {

    }

    private fun setupAdapter() {
        setupAdapterCategory()
        setupAdapterEvent()
        setupAdapterTicket()
    }
    private fun setupAdapterCategory() {



    }

    private fun setupAdapterEvent() {
        eventAdapter = EventAdapter { item ->



        }

        binding.recyclerEvent.layoutManager = LinearLayoutManager(context)
        binding.recyclerEvent.adapter = eventAdapter
        binding.recyclerEvent.setHasFixedSize(true)
    }

    private fun setupAdapterTicket() {

    }

    fun generateCategories(): List<CategoryItem> {
        return listOf(
            CategoryItem(1, "Âm nhạc"),
            CategoryItem(2, "Thể thao"),
            CategoryItem(3, "Hội thảo"),
            CategoryItem(4, "Giải trí"),
            CategoryItem(5, "Triển lãm"),
            CategoryItem(6, "Giáo dục")
        )
    }
}
