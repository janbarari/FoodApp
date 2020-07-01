package io.github.janbarari.foodapp.helper

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

/**
 * External functions for DRY
 */

fun Activity.selfRestart() {
    val intent = this.intent
    this.finish()
    this.startActivity(intent)
}

fun View.hide() {
    this.animate().alpha(0f).duration = 150
}

fun View.show() {
    this.animate().alpha(1f).duration = 150
}

fun CoordinatorLayout.show(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Activity.forceClearFocus(editText: EditText) {
    editText.clearFocus()
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}