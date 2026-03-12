package com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender.detailRegister

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
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentDetailRegisterBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender.FindViewModel
import com.uzuu.learn19_5_bottomnav_basic.feature.main.MainActivity
import com.uzuu.learn19_5_bottomnav_basic.ui.component.DateTimePickerHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailRegisterFragment: Fragment() {
    private var _binding : FragmentDetailRegisterBinding ?= null

    val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).container.timeRepository
        DetailFactory(repo)
    }

    private val args : DetailRegisterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = args.timeId
        println("DEBUG in detailFM id nhan ve $id")

        setButton(id)
        if (viewModel.detailState.value.times == null) {
            viewModel.getTimeById(id)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailState.collect { state ->
                    println("DEBUG in detailFM $state")
                    state.times?.let { time ->

                        binding.edtStartDay.setText(format(time.timeStart))
                        binding.edtEndDay.setText(format(time.timeEnd))
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailEvent.collect { event ->
                    when(event) {
                        is DetailViewModel.DetailEvent.Toast -> {
                            Toast.makeText(context,event.mess, Toast.LENGTH_SHORT).show()
                        }
                        is DetailViewModel.DetailEvent.clear -> {
                            binding.edtStartDay.setText("")
                            binding.edtEndDay.setText("")
                        }
                        is DetailViewModel.DetailEvent.navigateBack -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun setButton(id: Int) {
        binding.edtStartDay.setOnClickListener {

            val currentTime = viewModel.detailState.value.times?.timeStart

            DateTimePickerHelper.show(
                parentFragmentManager,
                currentTime
            ) { newTime ->

                binding.edtStartDay.setText(format(newTime))
            }
        }

        binding.edtEndDay.setOnClickListener {

            val currentTime = viewModel.detailState.value.times?.timeEnd

            DateTimePickerHelper.show(
                parentFragmentManager,
                currentTime
            ) { newTime ->

                binding.edtEndDay.setText(format(newTime))
            }
        }

        binding.btnSaveDateTime.setOnClickListener {
            viewModel.onClickSave(id,
                binding.edtStartDay.text.toString().trim(),
                binding.edtEndDay.text.toString().trim()
            )
        }
    }
    private fun format(time: Long): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(Date(time))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}