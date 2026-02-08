package com.uzuu.learn14_roomdb.ui.main

import android.os.Message

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
}