package com.troshchiy.verificationedittext

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verificationEditText.onApplyCodeClick = {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
        verificationEditText.onDeleteCodeClick = {
            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
        }

        btn_loading.setOnClickListener {
            verificationEditText.setLoading()
        }
        btn_error.setOnClickListener {
            verificationEditText.setError("Some error message")
        }
        btn_success.setOnClickListener {
            val message = "All set! This promo has been applied to all eligible items."
            verificationEditText.setSuccess(message)
        }
    }
}
