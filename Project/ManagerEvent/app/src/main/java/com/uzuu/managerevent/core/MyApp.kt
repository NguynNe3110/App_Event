package com.uzuu.managerevent.core

import android.app.Application
import com.uzuu.managerevent.data.session.SessionManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
    }
}