package com.troshchiy.verificationedittextlibrary

import android.content.Context
import android.content.res.ColorStateList
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

    var helperText: String = ""
        set(value) {
            field = value
            textInputLayout.helperText = field
        }

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    private val normalColor = ContextCompat.getColor(context, R.color.normal)
    private val selectedColor = ContextCompat.getColor(context, R.color.selected)
    private val errorColor = ContextCompat.getColor(context, R.color.tv_error)

    //TODO: delete
    private val testColor = ContextCompat.getColor(context, R.color.test)

    private val hintStateList = colorStateListOf(selectedColor)
    private val errorStateList = colorStateListOf(errorColor)

    private val endIconSelectedTintList = colorStateListOf(
        /* enabled */ intArrayOf(android.R.attr.state_enabled) to selectedColor,
        /* disabled */ intArrayOf(-android.R.attr.state_enabled) to selectedColor,
        /* unchecked */ intArrayOf(-android.R.attr.state_checked) to selectedColor,
        /* pressed */ intArrayOf(android.R.attr.state_pressed) to selectedColor
    )

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

        textInputLayout.hintTextColor = hintStateList
        textInputLayout.boxStrokeColor = selectedColor
        textInputLayout.setErrorTextColor(errorStateList)

        textInputLayout.editText?.doOnTextChanged { text, start, count, after ->
            setError(null)
            textInputLayout.isEndIconCheckable = true
            textInputLayout.setEndIconDrawable(R.drawable.ic_close)

            textInputLayout.helperText = null
        }

        textInputEditText = findViewById(R.id.textInputEditText)
    }

    fun setError(message: String?) {
        textInputLayout.error = message
    }

    fun setSuccess() {
        setError(null)
        textInputLayout.isEndIconCheckable = false
        textInputLayout.setEndIconTintList(endIconSelectedTintList)
        textInputLayout.setEndIconDrawable(R.drawable.ic_checkmark)
    }
}