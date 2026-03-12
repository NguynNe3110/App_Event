package com.uzuu.learn19_4_practice_nav_isolation.feature.detail

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
import com.uzuu.learn19_4_practice_nav_isolation.R
import com.uzuu.learn19_4_practice_nav_isolation.databinding.FragmentDetailBinding
import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.MainActivity
import kotlinx.coroutines.launch

class DetailFragment: Fragment() {
    private var _binding: FragmentDetailBinding?= null
    private val binding get() = _binding!!

    // khoi tao viewmodel
    private val detailViewModel: DetailViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).productRepository
        DetailVMFactory(repo)
    }

    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButton()
        detailViewModel.loadProduct(args.productId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    detailViewModel.detailState.collect { state ->
                        // hayyyyyyyyyyyyyy
                        state.product?.let { product ->
                            if (binding.edtNameDetail.text.isNullOrEmpty()) {
                                binding.edtNameDetail.setText(state.product?.name)
                            }
                            if (binding.edtPriceDetail.text.isNullOrEmpty()) {
                                binding.edtPriceDetail.setText(state.product?.price.toString())
                            }
                            if (binding.edtDescriptionDetail.text.isNullOrEmpty()) {
                                binding.edtDescriptionDetail.setText(state.product?.description)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    detailViewModel.detailEvent.collect { event ->
                        when(event) {
                            is DetailUiEvent.Toast -> {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }
                            is DetailUiEvent.onPopStack -> {
                                findNavController().popBackStack()
                            }
                            is DetailUiEvent.opPopToHome -> {
                                findNavController().popBackStack(R.id.homeFragment, false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupButton() {
        binding.btnUpdateDetail.setOnClickListener {
            detailViewModel.onUpdate(
                Products(
                    args.productId,
                    binding.edtNameDetail.text.toString(),
                    binding.edtPriceDetail.text.toString().toLongOrNull() ?: 0,
                    binding.edtDescriptionDetail.text.toString()
                )
            )
        }

        binding.btnDeleteDetail.setOnClickListener {
            detailViewModel.onDelete(
                args.productId
            )
        }

        binding.btnBackDetail.setOnClickListener {
            detailViewModel.onBackStack()
        }

        binding.btnGoHomeDetail.setOnClickListener {
            detailViewModel.onBackHome()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}