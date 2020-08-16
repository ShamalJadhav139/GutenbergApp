package com.etpl.newbase.view.appBase

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment:Fragment() {

    /**
     * Show Keyboard method
     */
    fun showSoftKeyboard() {
        val inputMethodManager =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * hide keyboard method
     */
    fun hideSoftKeyboard() {
        val inputMethodManager = activity!!.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (activity!!.currentFocus != null)
            inputMethodManager.hideSoftInputFromWindow(
                activity!!.currentFocus!!.windowToken, 0
            )
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



}