package com.mylibrarykotlin.test.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.mylibrarykotlin.R
import com.mylibrarykotlin.common.MyApp
import kotlinx.coroutines.*


class ForegroundService : Service() {

    private val TAG = ForegroundService::class.java.simpleName

    companion object {
        /**
         * 标记服务是否启动
         */
        var serviceIsLive = false

        var myCallback: MyCallback? = null

        interface MyCallback {
            fun getData(str: String)
        }
    }

    /**
     * 唯一前台通知ID
     */
    private val NOTIFICATION_ID = 1000
    private var builder: NotificationCompat.Builder? = null

    private val newDataBinder = NewDataBinder()

    override fun onCreate() {
        super.onCreate()
        // 获取服务通知
        builder = createForegroundNotification()
        //将服务置于启动状态 ,NOTIFICATION_ID指的是创建的通知的ID
        builder?.let {
            startForeground(NOTIFICATION_ID, it.build())
            startPresenting()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        // 标记服务启动
        serviceIsLive = true
        return newDataBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        // 标记服务启动
        serviceIsLive = true
        // 数据获取
        val data = intent!!.getStringExtra("Foreground")
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    fun stopService() {
        // 移除通知
        stopForeground(true);
        stopPresenting()
        serviceIsLive = false
    }

    fun unbinding(){
        myCallback = null
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy");
        // 标记服务关闭
        super.onDestroy()
    }

    /**
     * 创建服务通知
     */
    private fun createForegroundNotification(): NotificationCompat.Builder {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // 唯一的通知通道的id.
        val notificationChannelId = "notification_channel_id_01"

        // Android8.0以上的系统，新建消息通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //用户可见的通道名称
            val channelName = "重要消息通知"
            //通道的重要程度
            val importance = NotificationManager.IMPORTANCE_MIN
            val notificationChannel = NotificationChannel(notificationChannelId, channelName, importance)
            notificationChannel.description = "这里是通知的描述"
            //LED灯
//            notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.RED
            //震动
//            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
//            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder = NotificationCompat.Builder(this, notificationChannelId)
        //通知小图标
        builder.setSmallIcon(R.drawable.ic_launcher)
        //通知标题
        builder.setContentTitle("通知标题")
        //通知内容
        builder.setContentText("通知内容")
        //设定通知显示的时间
        builder.setWhen(System.currentTimeMillis())
        //设定启动的内容
        val activityIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        //创建通知并返回
        return builder
    }

    private var second = 1

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private var job: Job? = null

    private fun startPresenting() {
        job = loadData()
    }

    private fun stopPresenting() {
        job?.cancel()
    }

    private fun loadData() = uiScope.launch {
        while (serviceIsLive) {
            builder?.let {
                second++
                var tip =""
                if (MyApp.isForeground){
                    tip ="前台-通知内容:$second"
                }else{
                    tip ="后台-通知内容:$second"
                }
                it.setContentText(tip)
                startForeground(NOTIFICATION_ID, it.build())
                myCallback?.getData(tip)
            }
            delay(2000)
        }
    }

    inner class NewDataBinder : Binder() {

        val service: ForegroundService
            get() = this@ForegroundService
    }

    fun setCallback(c: MyCallback) {
        myCallback = c
    }
}