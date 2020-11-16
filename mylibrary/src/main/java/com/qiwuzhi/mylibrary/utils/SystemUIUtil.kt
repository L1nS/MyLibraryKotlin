package com.qiwuzhi.mylibrary.utils

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.imyyq.mvvm.app.BaseApp
import com.imyyq.mvvm.utils.DensityUtil
import com.imyyq.mvvm.utils.SystemUIUtil


object SystemUIUtil{

    /**
     * 修改状态栏颜色，支持5.0以上版本
     */
    fun setStatusBarColor(window: Window, colorId: String, becomeLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(colorId)
            if (becomeLight) {
                //实现状态栏图标和文字颜色为浅色
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            } else {
                //实现状态栏图标和文字颜色为暗色
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    /**
     * 获取导航栏的高度
     *
     * @return
     */
    fun getStatusBarHeight(): Int {
        val resources: Resources = BaseApp.getInstance().applicationContext.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    fun fitsSystemWindows(view: View) {
        val lp = view.layoutParams as FrameLayout.LayoutParams
        lp.width = FrameLayout.LayoutParams.MATCH_PARENT
        lp.height = getStatusBarHeight() + DensityUtil.dp2px(48f)
        view.layoutParams = lp
    }
}