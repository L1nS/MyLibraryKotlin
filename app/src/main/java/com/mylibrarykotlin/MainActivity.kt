package com.mylibrarykotlin

import android.content.Intent
import android.os.SystemClock
import android.view.KeyEvent
import com.mylibrary.base.BaseActivity
import com.mylibrary.utils.ToastUtil
import com.mylibrarykotlin.common.NoViewModel
import com.mylibrarykotlin.databinding.ActivityMainBinding
import com.mylibrarykotlin.test.notification.NotificationActivity
import kotlin.system.exitProcess


class MainActivity : BaseActivity<ActivityMainBinding, NoViewModel>(
        R.layout.activity_main) {

    private var mExitTime: Long = 0

    override fun initData() {
        mBinding.idBtnNotification.setOnClickListener {

            startActivity(Intent(this, NotificationActivity::class.java))
        }


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