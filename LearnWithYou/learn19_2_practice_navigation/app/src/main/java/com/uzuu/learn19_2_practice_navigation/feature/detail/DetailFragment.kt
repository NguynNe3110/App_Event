package com.uzuu.learn19_2_practice_navigation.feature.detail

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
import androidx.navigation.fragment.navArgs
import com.uzuu.learn19_2_practice_navigation.databinding.FragmentDetailBinding
import com.uzuu.learn19_2_practice_navigation.feature.borrow.BorrowViewModel
import com.uzuu.learn19_2_practice_navigation.feature.main.MainActivity
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private val vm: BorrowViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 1) Lấy itemId từ SafeArgs
        val itemId = args.itemId

        // 2) Tell ViewModel chọn item => dữ liệu detail lấy từ StateFlow (không truyền object)
        vm.selectItem(itemId)

        // 3) Render UI theo UiState
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.uiState.collect { st ->
                        val item = st.selectedItem
                        binding.tvDetail.text =
                            if (item == null) "Item not found"
                            else "Detail: ${item.name}\nAvailable: ${item.available}"
                        binding.tvQty.text = st.borrowQty.toString()
                    }
                }
            }
        }

        binding.btnPlus.setOnClickListener { vm.incQty() }
        binding.btnMinus.setOnClickListener { vm.decQty() }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToConfirmFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}