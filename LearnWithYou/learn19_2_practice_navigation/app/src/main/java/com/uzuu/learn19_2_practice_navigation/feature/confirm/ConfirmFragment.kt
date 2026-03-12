package com.uzuu.learn19_2_practice_navigation.feature.confirm

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
import com.uzuu.learn19_2_practice_navigation.databinding.FragmentConfirmBinding
import com.uzuu.learn19_2_practice_navigation.feature.borrow.BorrowViewModel
import com.uzuu.learn19_2_practice_navigation.feature.main.MainActivity
import kotlinx.coroutines.launch

class ConfirmFragment : Fragment() {

    private var _binding: FragmentConfirmBinding? = null
    private val binding get() = _binding!!

    private val vm: BorrowViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.uiState.collect { st ->
                        val item = st.selectedItem
                        val msg = st.message
                        binding.tvConfirm.text = buildString {
                            appendLine("CONFIRM")
                            appendLine("Item: ${item?.name ?: "?"}")
                            appendLine("Qty: ${st.borrowQty}")
                            appendLine("Available now: ${item?.available ?: "?"}")
                            if (msg != null) appendLine("\nMessage: $msg")
                        }
                    }
                }
            }
        }

        binding.btnConfirm.setOnClickListener {
            vm.confirmBorrow()
            findNavController().clearBackStack(com.uzuu.learn19_2_practice_navigation.R.id.listFragment)
        }

        binding.btnBackToList.setOnClickListener {
            // pop về List: vì stack đang [List, Detail, Confirm]
            findNavController().popBackStack(
                destinationId = com.uzuu.learn19_2_practice_navigation.R.id.listFragment,
                inclusive = false
            )
            vm.clearMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}