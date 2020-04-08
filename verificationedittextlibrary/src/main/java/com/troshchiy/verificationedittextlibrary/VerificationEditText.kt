package com.troshchiy.verificationedittextlibrary

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Toast
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

    private val normalColor = ContextCompat.getColor(context, R.color.normal)
    private val selectedColor = ContextCompat.getColor(context, R.color.selected)
    private val errorColor = ContextCompat.getColor(context, R.color.tv_error)
    private val testColor = ContextCompat.getColor(context, R.color.test)

    private val errorStateList = colorStateListOf(errorColor)
    private val hintStateList = colorStateListOf(selectedColor)
    private val counterStateList = colorStateListOf(selectedColor)

//    private val counterTextStateList = colorStateListOf(
//        /* enabled */ intArrayOf(android.R.attr.state_enabled) to testColor,
//        /* disabled */ intArrayOf(-android.R.attr.state_enabled) to testColor,
//        /* unchecked */ intArrayOf(-android.R.attr.state_checked) to testColor,
//        /* pressed */ intArrayOf(android.R.attr.state_pressed) to testColor
//    )

    private val colorStateList = colorStateListOf(testColor)

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

//        textInputLayout.setErrorTextColor(errorStateList)
//        textInputLayout.boxStrokeColor = selectedColor
//
//        textInputLayout.hintTextColor = hintStateList
//        textInputLayout.counterOverflowTextColor = counterStateList

        textInputLayout.editText?.doOnTextChanged { text, start, count, after ->
            setError("")
        }

        textInputEditText = findViewById(R.id.textInputEditText)

        textInputLayout.setEndIconOnClickListener {
            textInputLayout.editText?.text = null

            Toast.makeText(context, "Click on ICON", Toast.LENGTH_SHORT).show()
        }
    }

    fun setError(message: String) {
        textInputLayout.error = message
    }

    fun setSuccess() {
        setError("")
        //TODO:
    }
}