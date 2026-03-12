package com.uzuu.learn19_2_practice_navigation.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.uzuu.learn19_2_practice_navigation.databinding.FragmentListBinding
import com.uzuu.learn19_2_practice_navigation.feature.borrow.BorrowViewModel
import com.uzuu.learn19_2_practice_navigation.feature.main.MainActivity
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val vm: BorrowViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Render list from StateFlow (bài 10-15)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.uiState.collect { st ->
                        binding.tvList.text = st.items.joinToString("\n") {
                            "• ${it.name} (id=${it.id}) - available=${it.available}"
                        }
                    }
                }
            }
        }

        fun openDetail(id: Int) {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(id)
            // start view moi
            findNavController().navigate(action)
        }

        binding.btnItem1.setOnClickListener { openDetail(1) }
        binding.btnItem2.setOnClickListener { openDetail(2) }
        binding.btnItem3.setOnClickListener { openDetail(3) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}