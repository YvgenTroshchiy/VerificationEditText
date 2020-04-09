package com.troshchiy.verificationedittext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_error.setOnClickListener {
            verificationEditText.setError("Some error message")
        }
        btn_success.setOnClickListener {
            val message = "All set! This promo has been applied to all eligible items."
            verificationEditText.setSuccessState(message)
        }
    }
}
