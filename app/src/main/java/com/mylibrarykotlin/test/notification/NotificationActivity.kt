package com.mylibrarykotlin.test.notification

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.imyyq.mvvm.app.BaseApp
import com.mylibrary.base.BaseActivity
import com.mylibrarykotlin.R
import com.mylibrarykotlin.common.NoViewModel
import com.mylibrarykotlin.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity<ActivityNotificationBinding, NoViewModel>(
        R.layout.activity_notification) {

    private val TAG = "NotificationActivity"
    private var mForegroundService: Intent? = null
    private var newDataBinder: ForegroundService.NewDataBinder? = null
    private var isBind = false

    override fun initData() {
        mBinding.idBtnStart.setOnClickListener {
            if (!ForegroundService.serviceIsLive) {
                // Android 8.0使用startForegroundService在前台启动新服务
                mForegroundService = Intent(BaseApp.getInstance(), ForegroundService::class.java)
                mForegroundService!!.putExtra("Foreground", "This is a foreground service.")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    BaseApp.getInstance().startForegroundService(mForegroundService)
                } else {
                    BaseApp.getInstance().startService(mForegroundService)
                }
                Log.e(TAG, "服务启动")
            } else {
                Toast.makeText(this, "前台服务正在运行中...", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.idBtnBinding.setOnClickListener {
            mForegroundService = Intent(BaseApp.getInstance(), ForegroundService::class.java)
            bindService(mForegroundService, connection, BIND_AUTO_CREATE)
            Log.e(TAG, "服务绑定")
        }

        mBinding.idBtnUnbinding.setOnClickListener {
            if (isBind) {
                newDataBinder?.service?.unbinding()
                unbindService(connection)
                isBind = false
            }
        }
        mBinding.idBtnStop.setOnClickListener {
            // 停止服务
            newDataBinder?.service?.stopService()
            mForegroundService = Intent(BaseApp.getInstance(), ForegroundService::class.java)
            BaseApp.getInstance().stopService(mForegroundService)
            Log.e(TAG, "服务停止")
        }
    }

    private val connection:ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBind = true
            newDataBinder = service as ForegroundService.NewDataBinder?
            val myService = newDataBinder?.service
            myService?.setCallback(object : ForegroundService.Companion.MyCallback {
                override fun getData(str: String) {
                    mBinding.idTvMsg.text = str
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }

    override fun onResume() {
        super.onResume()
        if (ForegroundService.serviceIsLive) {
            Log.e(TAG, "服务绑定：onResume()")
            mForegroundService = Intent(BaseApp.getInstance(), ForegroundService::class.java)
            bindService(mForegroundService, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        if (isBind) {
            newDataBinder?.service?.unbinding()
            unbindService(connection)
            Log.e(TAG, "服务解绑")
        }
        mForegroundService = null
        newDataBinder = null
        isBind = false
        super.onStop()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            newDataBinder?.service?.stopService()
            mForegroundService = Intent(BaseApp.getInstance(), ForegroundService::class.java)
            BaseApp.getInstance().stopService(mForegroundService)
        }
        return super.onKeyDown(keyCode, event)
    }
}