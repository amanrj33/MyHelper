package com.aman.androidlibrary.my_helper.support.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import androidx.core.view.isVisible


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.text(): String = text.toString()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.changeEnable(isEnabled: Boolean) {
    this.isEnabled = isEnabled
}

fun View.disable(applyBackground: Boolean = false, backgroundRes: Int) {
    isEnabled = false
    if (applyBackground) {
        setBackgroundResource(backgroundRes)
    }
}

fun View.enable(applyBackground: Boolean = false, backgroundRes: Int) {
    isEnabled = true
    if (applyBackground) {
        setBackgroundResource(backgroundRes)
    }
}

fun View.hide() {
    isVisible = false
}

fun View.show() {
    isVisible = true
}

fun View.switchVisibility() {
    isVisible = !isVisible
}

fun View.performReveal() {
    if (isVisible) hideToCenter() else revealFromCenter()
}

fun View.performHalfTurn(duration: Long = 500) {
    animate()
        .rotationBy(if (rotation == 0f) -180f else 180f)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.revealFromCenter(duration: Long = 500) {
    // Make sure the view is initially invisible
    this.visibility = View.INVISIBLE

    // Scale the view from 0 to its full width
    this.scaleX = 0f
    this.scaleY = 0f

    this.pivotX = this.width / 2f
    this.pivotY = this.height / 2f

    // Animate the scaling
    this.animate()
        .scaleX(1f)
        .scaleY(1f)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withStartAction {
            this.visibility = View.VISIBLE
        }
        .start()
}

fun View.hideToCenter(duration: Long = 500) {
    // Scale the view to 0 width
    this.pivotX = this.width / 2f
    this.pivotY = this.height / 2f

    // Animate the scaling
    this.animate()
        .scaleX(0f)
        .scaleY(0f)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction {
            this.visibility = View.GONE
        }
        .start()
}

fun View.performDropdown() {
    if (isVisible) collapseDropdown() else expandDropdown()
}

fun View.expandDropdown(duration: Long = 500) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    layoutParams.height = 0
    visibility = View.VISIBLE
    animate()
        .translationY(0f)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            requestLayout()
        }
}

fun View.collapseDropdown(duration: Long = 500) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    layoutParams.height = 0
    visibility = View.GONE
    animate()
        .translationY((-measuredHeight).toFloat())
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction {
            visibility = View.GONE
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            requestLayout()
        }
}

fun CompoundButton.assignCheck(isChecked: Boolean) {
    this.isChecked = isChecked
}

fun CompoundButton.toggleCheck() {
    isChecked = !isChecked
}

fun CompoundButton.check() {
    isChecked = true
}

fun CompoundButton.uncheck() {
    isChecked = false
}

fun EditText.clear() {
    setText("")
}