package com.qiwuzhi.mylibrary.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.imyyq.mvvm.base.BaseModel
import com.imyyq.mvvm.base.BaseViewModel
import com.imyyq.mvvm.base.DataBindingBaseActivity
import com.qiwuzhi.mylibrary.R

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel<out BaseModel>>(
    @LayoutRes private val layoutId: Int,
    private val varViewModelId: Int? = null
) : DataBindingBaseActivity<V, VM>(layoutId,varViewModelId) {

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.colorNavBar)
            .navigationBarDarkIcon(true)
            .init()
    }
}