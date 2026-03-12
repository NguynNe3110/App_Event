package com.uzuu.learn4_dialog_basic

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import android.widget.Button
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShow = findViewById<Button>(R.id.btnShow)

        btnShow.setOnClickListener {

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_test)

            dialog.show()

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}