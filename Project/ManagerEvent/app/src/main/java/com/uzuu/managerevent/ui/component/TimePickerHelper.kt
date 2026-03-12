package com.uzuu.managerevent.ui.component

import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

object TimePickerHelper {

    fun showTimePicker(
        parentFragmentManager : FragmentManager
    ) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)   // ⭐ 24h
            .setHour(23)
            .setMinute(59)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK) // ⭐ vòng tròn
            .setTitleText("Chọn thời gian")
            .build()

        picker.show(parentFragmentManager, "TIME_PICKER")

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute

        }











    }
}