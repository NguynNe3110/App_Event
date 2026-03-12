package com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentFindBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.main.MainActivity
import com.uzuu.learn19_5_bottomnav_basic.ui.component.DateTimePickerHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale


class FindFragment: Fragment() {
    //  man hinh dung material datetime de chon ngay dang ki

    private var _binding : FragmentFindBinding ?= null
    val binding get() = _binding!!

    private val viewModel: FindViewModel by viewModels {
        val repo = (requireActivity() as MainActivity).container.timeRepository
        FindVMFactory(repo)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setButton()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.findState.collect { state ->

                    state.dateTimeStart?.let {
                        binding.edtStartDay.setText(format(it))
                    }

                    state.dateTimeEnd?.let {
                        binding.edtEndDay.setText(format(it))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.findEvent.collect { event ->
                    when(event) {
                        is FindViewModel.FindUiEvent.Toast -> {
                            Toast.makeText(context,event.mess, Toast.LENGTH_SHORT).show()

                        }

                        is FindViewModel.FindUiEvent.clear -> {
                            binding.edtStartDay.setText("")
                            binding.edtEndDay.setText("")
                        }
                    }
                }
            }
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

    private fun setButton() {
        binding.edtStartDay.setOnClickListener {

            DateTimePickerHelper.show(parentFragmentManager) { time ->

                viewModel.setStartDateTime(time)
            }
        }

        binding.edtEndDay.setOnClickListener {

            DateTimePickerHelper.show(parentFragmentManager) { time ->

                viewModel.setEndDateTime(time)
            }
        }

        binding.btnSaveDateTime.setOnClickListener {
            val st = binding.edtStartDay.text.toString().trim()
            val e = binding.edtEndDay.text.toString().trim()

            println("DEBUG in findFM $st & $e")
            viewModel.onClickSave(
                st,
                e
            )
        }
    }


}