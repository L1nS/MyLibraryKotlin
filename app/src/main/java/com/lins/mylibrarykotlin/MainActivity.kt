package com.lins.mylibrarykotlin

import android.os.SystemClock
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lins.mylibrarykotlin.common.NoViewModel
import com.lins.mylibrarykotlin.databinding.ActivityMainBinding
import com.qiwuzhi.mylibrary.base.BaseActivity
import com.qiwuzhi.mylibrary.utils.ToastUtil
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityMainBinding, NoViewModel>(
    R.layout.activity_main) {

    private var homeFragment: Fragment? = null
    private var shopFragment: Fragment? = null
    private var cartFragment: Fragment? = null
    private var userFragment: Fragment? = null
    private lateinit var fm: FragmentManager

    private var mExitTime: Long = 0

    override fun initData() {
        fm = supportFragmentManager
        mBinding.idLyHome.setOnClickListener { setTabSelection(0) }
        mBinding.idLyShop.setOnClickListener { setTabSelection(1) }
        mBinding.idLyCart.setOnClickListener { setTabSelection(2) }
        mBinding.idLyUser.setOnClickListener { setTabSelection(3) }
        setTabSelection(0)
    }

    private fun setTabSelection(index: Int) {
        resetBtn(index)
        //开启一个Fragment事务
        val ft = fm.beginTransaction()
        //先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragment(ft)
        when (index) {
            0 -> {
                
            }
            1 -> {
                
            }
            2 -> {
                
            }
            3 -> {
            }
        }
        ft.commit()
    }

    private fun resetBtn(index: Int) {
        mBinding.idImgHome.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.holo_blue_light
            )
        )
        mBinding.idImgShop.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.holo_blue_light
            )
        )
        mBinding.idImgCart.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.holo_blue_light
            )
        )
        mBinding.idImgUser.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.holo_blue_light
            )
        )
        mBinding.idTvHome.setTextColor(ContextCompat.getColor(this, R.color.colorNavTextOff))
        mBinding.idTvShop.setTextColor(ContextCompat.getColor(this, R.color.colorNavTextOff))
        mBinding.idTvCart.setTextColor(ContextCompat.getColor(this, R.color.colorNavTextOff))
        mBinding.idTvUser.setTextColor(ContextCompat.getColor(this, R.color.colorNavTextOff))
        when (index) {
            0 -> {
                mBinding.idImgHome.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        android.R.color.holo_blue_dark
                    )
                )
                mBinding.idTvHome.setTextColor(ContextCompat.getColor(this, R.color.colorTheme))
            }
            1 -> {
                mBinding.idImgShop.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        android.R.color.holo_blue_dark
                    )
                )
                mBinding.idTvShop.setTextColor(ContextCompat.getColor(this, R.color.colorTheme))
            }
            2 -> {
                mBinding.idImgCart.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        android.R.color.holo_blue_dark
                    )
                )
                mBinding.idTvCart.setTextColor(ContextCompat.getColor(this, R.color.colorTheme))
            }
            3 -> {
                mBinding.idImgUser.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        android.R.color.holo_blue_dark
                    )
                )
                mBinding.idTvUser.setTextColor(ContextCompat.getColor(this, R.color.colorTheme))
            }
        }
    }

    private fun hideFragment(ft: FragmentTransaction) {
        if (homeFragment != null) ft.hide(homeFragment!!)
        if (shopFragment != null) ft.hide(shopFragment!!)
        if (cartFragment != null) ft.hide(cartFragment!!)
        if (userFragment != null) ft.hide(userFragment!!)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((SystemClock.elapsedRealtime() - mExitTime) > 2000) {
                ToastUtil.showCustomLongToast("再按一次退出程序")
                mExitTime = SystemClock.elapsedRealtime()
            } else {
                finish()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}