package com.troshchiy.verificationedittextlibrary

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


fun colorStateListOf(@ColorInt color: Int): ColorStateList {
    return ColorStateList.valueOf(color)
}

fun colorStateListOf(vararg mapping: Pair<IntArray, Int>): ColorStateList {
    val (states, colors) = mapping.unzip()
    return ColorStateList(states.toTypedArray(), colors.toIntArray())
}

class VerificationEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    private val states = arrayOf(
        /* enabled */ intArrayOf(android.R.attr.state_enabled),
        /* disabled */ intArrayOf(-android.R.attr.state_enabled),
        /* unchecked */ intArrayOf(-android.R.attr.state_checked),
        /* pressed */ intArrayOf(android.R.attr.state_pressed)
    )

    private val colors = intArrayOf(
        /* enabled */ Color.BLUE,
        /* disabled */ Color.BLUE,
        /* unchecked */ Color.BLUE,
        /* pressed */ Color.BLUE
    )

    private val errorColor = ContextCompat.getColor(context, R.color.tv_error)
    private val testColor = ContextCompat.getColor(context, R.color.test)

    private val errorStateList = colorStateListOf(errorColor)

    private val colorStateList = colorStateListOf(testColor)

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

        textInputLayout.setErrorTextColor(errorStateList)

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


//        textInputLayout.boxStrokeColor = testColor // Selected stroke color
//        textInputLayout.counterTextColor = colorStateList
        textInputLayout.counterOverflowTextColor = colorStateList

    }
}