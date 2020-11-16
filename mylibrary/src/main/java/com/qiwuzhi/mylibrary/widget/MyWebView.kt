package com.qiwuzhi.mylibrary.widget

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class MyWebView : WebView {
    private var mOnScrollChangedCallback: OnScrollChangedCallback? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback!!.onScroll(l, t, l - oldl, t - oldt)
        }
    }

    fun setOnScrollChangedCallback(callback: OnScrollChangedCallback?) {
        mOnScrollChangedCallback = callback
    }

    interface OnScrollChangedCallback {
        fun onScroll(dx: Int, dy: Int, dx_change: Int, dy_change: Int)
    }
}