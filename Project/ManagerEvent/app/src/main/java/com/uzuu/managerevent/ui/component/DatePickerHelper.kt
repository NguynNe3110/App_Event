package com.uzuu.managerevent.ui.component

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker

object DatePickerHelper {

    fun showDatePicker(
        parentFragmentManager : FragmentManager
    ) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Chọn ngày")
            .build()

        datePicker.show(parentFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { date ->
        }
    }
}