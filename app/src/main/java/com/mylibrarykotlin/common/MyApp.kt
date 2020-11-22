package com.mylibrarykotlin.common

import android.app.Activity
import android.os.Bundle
import com.imyyq.mvvm.app.AppActivityManager
import com.imyyq.mvvm.app.BaseApp
import com.imyyq.mvvm.http.HttpRequest
import com.mylibrarykotlin.R
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter


class MyApp : BaseApp() {
    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorLoading, R.color.colorWhite) //全局设置主题颜色
            MaterialHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun onCreate() {
        super.onCreate()
        // 网络请求需设置 baseUrl
        HttpRequest.mDefaultBaseUrl = Constants.HTTP_URL
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }


    /**
     * 当前Acitity个数
     */
    private var activityAccount = 0
    companion object{
        var isForeground = true
    }

    /**
     * Activity 生命周期监听，用于监控app前后台状态切换
     */
    private var activityLifecycleCallbacks: ActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {
            activityAccount++
            isForeground = true
        }

        override fun onActivityDestroyed(activity: Activity) {
            AppActivityManager.remove(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
            activityAccount--
            if (activityAccount == 0) {
                isForeground = false
            }
        }

        override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
        ) {
            AppActivityManager.add(activity)
        }

        override fun onActivityResumed(activity: Activity) {
        }

    }

}