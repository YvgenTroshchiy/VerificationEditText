package com.troshchiy.verificationedittextlibrary

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
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

    var onApplyCodeClick: (String) -> Unit = {}
    var onDeleteCodeClick: () -> Unit = {}

    private var isCodeApplied: Boolean = false

    private val debounceHandler = Handler()

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var progressBar: ProgressBar

    private val grayColor = ContextCompat.getColor(context, R.color.gray)
    private val greenColor = ContextCompat.getColor(context, R.color.green)
    private val redColor = ContextCompat.getColor(context, R.color.red)

    private val grayHintStateList = colorStateListOf(grayColor)
    private val greenHintStateList = colorStateListOf(greenColor)
    private val redStateList = colorStateListOf(redColor)

    private val grayEndIconTintList = colorStateListOf(
        intArrayOf(android.R.attr.state_focused) to grayColor,
        intArrayOf(-android.R.attr.state_focused) to grayColor,
        intArrayOf(android.R.attr.state_enabled) to grayColor,
        intArrayOf(-android.R.attr.state_enabled) to grayColor
    )

    private val greenEndIconTintList = colorStateListOf(
        intArrayOf(android.R.attr.state_focused) to greenColor,
        intArrayOf(-android.R.attr.state_focused) to greenColor,
        intArrayOf(android.R.attr.state_enabled) to greenColor,
        intArrayOf(-android.R.attr.state_enabled) to grayColor
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

        setInitialStyle()

        textInputLayout.setErrorTextColor(redStateList)

        textInputLayout.editText?.doOnTextChanged { text, start, count, after ->
//            debounceHandler.removeCallbacks(null)
//            debounceHandler.postDelayed({
            setError(null)

            if (after > 0) {
                setDefaultStyle()
            } else {
                textInputLayout.endIconDrawable = null
            }
//            }, 400)
        }

        textInputLayout.setEndIconOnClickListener {
            setLoading()

            if (isCodeApplied) {
                onDeleteCodeClick()
            } else {
                onApplyCodeClick(textInputLayout.editText?.text.toString())
            }
        }

        progressBar = findViewById(R.id.progressBar)
    }

    fun setError(message: String?) {
        isCodeApplied = false

        disableLoading()
        textInputLayout.error = message
    }

    fun setSuccess(message: String?) {
        isCodeApplied = true

        disableLoading()
        setError(null)

        setSuccessStyle()
        textInputLayout.helperText = message
    }

    private fun setInitialStyle() {
        textInputLayout.boxStrokeColor = greenColor
        textInputLayout.hintTextColor = greenHintStateList

        textInputLayout.setEndIconTintList(greenEndIconTintList)

        textInputLayout.helperText = null
    }

    private fun setDefaultStyle() {
        textInputLayout.boxStrokeColor = greenColor
        textInputLayout.hintTextColor = greenHintStateList

        textInputLayout.setEndIconTintList(greenEndIconTintList)
        textInputLayout.setEndIconDrawable(R.drawable.ic_checkmark)

        textInputLayout.helperText = null
    }

    private fun setSuccessStyle() {
        textInputLayout.boxStrokeColor = grayColor
        textInputLayout.hintTextColor = grayHintStateList

        textInputLayout.setEndIconTintList(grayEndIconTintList)
        textInputLayout.setEndIconDrawable(R.drawable.ic_close)
    }

    fun setLoading() {
        setError(null)
        textInputLayout.endIconDrawable = null
        textInputLayout.helperText = null

        textInputLayout.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun disableLoading() {
        textInputLayout.isEnabled = true
        progressBar.visibility = View.GONE
    }
}