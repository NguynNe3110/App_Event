package com.uzuu.learn19_5_bottomnav_basic.ui.component

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

object DateTimePickerHelper {

    fun show(
        fragmentManager: FragmentManager,
        defaultTime: Long? = null,
        onResult: (Long) -> Unit
    ) {

        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Chọn ngày")

        defaultTime?.let {
            datePickerBuilder.setSelection(it)
        }

        val datePicker = datePickerBuilder.build()

        datePicker.show(fragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { date ->

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = defaultTime ?: date

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTitleText("Chọn thời gian")
                .build()

            timePicker.show(fragmentManager, "TIME_PICKER")

            timePicker.addOnPositiveButtonClickListener {

                val resultCalendar = Calendar.getInstance()
                resultCalendar.timeInMillis = date
                resultCalendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                resultCalendar.set(Calendar.MINUTE, timePicker.minute)

                onResult(resultCalendar.timeInMillis)
            }
        }
    }
}