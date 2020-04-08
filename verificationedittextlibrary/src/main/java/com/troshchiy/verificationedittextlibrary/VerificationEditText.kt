package com.troshchiy.verificationedittextlibrary

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class VerificationEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    init {
        inflate(context, R.layout.verification_edittext_widget, this)

        attrs?.let { initAttrs(it) }
        setupViews()

        setSuccess()
    }

    private fun initAttrs(attrs: AttributeSet) {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.CenteredIconButton)
//        a.recycle()
    }

    private fun setupViews() {
        textInputLayout = findViewById(R.id.textInputLayout)

        textInputLayout.editText?.doOnTextChanged { text, start, count, after ->
            setError("")
        }

        textInputEditText = findViewById(R.id.textInputEditText)
    }

    fun setError(message: String) {
        textInputLayout.error = message
    }

    fun setSuccess() {
        setError("")

        val states = arrayOf(
            /* enabled */ intArrayOf(android.R.attr.state_enabled),
            /* disabled */ intArrayOf(-android.R.attr.state_enabled),
            /* unchecked */ intArrayOf(-android.R.attr.state_checked),
            /* pressed */ intArrayOf(android.R.attr.state_pressed)
        )

//        val colors = intArrayOf(
//            /* enabled */ Color.BLACK,
//            /* disabled */ Color.RED,
//            /* unchecked */ Color.GREEN,
//            /* pressed */ Color.BLUE
//        )

        val colors = intArrayOf(
            /* enabled */ Color.BLUE,
            /* disabled */ Color.BLUE,
            /* unchecked */ Color.BLUE,
            /* pressed */ Color.BLUE
        )

        val testColor = ContextCompat.getColor(context, R.color.test)
        val colorStateList = ColorStateList(states, colors)

//        textInputLayout.boxStrokeColor = testColor // Selected stroke color
//        textInputLayout.counterTextColor = colorStateList
        textInputLayout.counterOverflowTextColor = colorStateList

    }
}