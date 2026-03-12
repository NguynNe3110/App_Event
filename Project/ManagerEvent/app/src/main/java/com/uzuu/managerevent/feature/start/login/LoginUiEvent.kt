package com.uzuu.managerevent.feature.start.login

sealed class LoginUiEvent {
    data class Toast(val message: String) : LoginUiEvent()

    data class navigateForget(val username: String) : LoginUiEvent()

    object navigateRegister : LoginUiEvent()

    object navigateHome : LoginUiEvent()
}