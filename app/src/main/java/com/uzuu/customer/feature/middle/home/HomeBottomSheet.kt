package com.uzuu.customer.feature.middle.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uzuu.customer.databinding.BottomsheetEventBinding
import com.uzuu.customer.domain.model.Event

class HomeBottomSheet(
    private val event: Event
) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.txtNameEvent.text = event.name
        binding.txtAddress.text = event.location
        binding.txtDateTimeStart.text = event.startTime
        binding.txtDateTimeEnd.text = event.endTime

        binding.txtViewDetail.setOnClickListener {
            // code here
        }

        binding.handleBar.setOnClickListener {
            //code here
        }

        binding.btnAddToCart.setOnClickListener {
            //code here
        }
        binding.btnBuyNow.setOnClickListener {
            //code here
        }
    }
}