package com.uzuu.learn19_5_bottomnav_basic.feature.profile

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
import androidx.navigation.fragment.navArgs
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentProfileBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.main.MainActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(){
    private var _binding : FragmentProfileBinding?= null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).container.productRepository
        ProfileVMFactory(repo)
    }

    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = args.productId
        setupButton(id)
        observeAll()
        viewModel.getProduct(id)
    }

    private fun setupButton(id: Int) {
        binding.btnDeleteDetail.setOnClickListener {

            viewModel.onClickDelete(id)
        }

        binding.btnUpdateDetail.setOnClickListener {
            val id = args.productId

            viewModel.onClickUpdate(
                id,
                binding.edtNameDetail.text.toString().trim(),
                binding.edtPriceDetail.text.toString().trim(),
                binding.edtDescriptionDetail.toString().trim()
            )
        }
    }

    private fun observeAll() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.profileState.collect { state ->
                        state.items?.let { product ->
                            if (binding.edtNameDetail.text.isNullOrEmpty()) {
                                binding.edtNameDetail.setText(state.items?.name)
                            }
                            if (binding.edtPriceDetail.text.isNullOrEmpty()) {
                                binding.edtPriceDetail.setText(state.items?.price.toString())
                            }
                            if (binding.edtDescriptionDetail.text.isNullOrEmpty()) {
                                binding.edtDescriptionDetail.setText(state.items?.description)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.profileEvent.collect { event ->
                        when(event) {
                            is ProfileViewModel.ProfileUiEvent.Toast -> {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }

                            is ProfileViewModel.ProfileUiEvent.NavigateBack -> {
                                findNavController().popBackStack()
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