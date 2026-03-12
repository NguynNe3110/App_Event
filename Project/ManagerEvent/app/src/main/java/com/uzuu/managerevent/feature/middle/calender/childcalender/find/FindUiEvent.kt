package com.uzuu.managerevent.feature.middle.calender.childcalender.find

sealed class FindUiEvent {

    data class Toast(val message: String) : FindUiEvent()

    object navigateBack : FindUiEvent()

    data class navigateWith(val id: Int) : FindUiEvent()
}