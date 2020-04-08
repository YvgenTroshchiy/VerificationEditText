package com.troshchiy.verificationedittextlibrary

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class VerificationEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var textInputLayout: TextInputLayout? = null
    private var textInputEditText: TextInputEditText? = null

    init {
        inflate(context, R.layout.verification_edittext_widget, this)

        attrs?.let { initAttrs(it) }
        setupViews()
    }

    private fun initAttrs(attrs: AttributeSet) {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.CenteredIconButton)
//        a.recycle()
    }

    private fun setupViews() {
        textInputLayout = findViewById(R.id.textInputLayout)
        textInputEditText = findViewById(R.id.textInputEditText)
    }

    fun setError(message: String) {
        textInputLayout?.error = message
    }

    fun setSuccess() {
    }
}