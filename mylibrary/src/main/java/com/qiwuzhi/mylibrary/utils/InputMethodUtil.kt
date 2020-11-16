package com.qiwuzhi.mylibrary.utils

import android.content.Context
import android.os.IBinder
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.imyyq.mvvm.app.BaseApp


object InputMethodUtil {

    /**
     * 关闭指定EditText的输入法
     *
     * @param editText 指定的EditText
     */
    fun closeInputMethod(editText: EditText) {
        val binder = editText.windowToken
        val methodManager = BaseApp.getInstance().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as? InputMethodManager
        methodManager?.hideSoftInputFromWindow(binder, 0)
    }

    fun closeInputMethod(binder: IBinder) {
        val methodManager = BaseApp.getInstance().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as? InputMethodManager
        methodManager?.hideSoftInputFromWindow(binder, 0)
    }

    /**
     * 弹出输入法对话框，并且焦点在指定的EditText上
     *
     * @param editText 指定的EditText
     */
    fun showInputMethod(editText: EditText) {
        // 弹出键盘
        editText.postDelayed({
            val methodManager = BaseApp.getInstance().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as? InputMethodManager
            methodManager?.showSoftInput(editText, 0)
        }, 200)
    }

    fun focusable(editText: EditText) {
        editText.postDelayed({
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
        }, 200)
    }

    /**
     * 禁止EditText输入空格
     * @param editText
     */
    fun setEditTextInhibitInputSpace(editText: EditText) {
        val filter =
            InputFilter { source, start, end, dest, dstart, dend -> if (source == " ") "" else null }
        editText.filters = arrayOf(filter)
    }
}
