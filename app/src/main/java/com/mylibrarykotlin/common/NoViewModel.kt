package com.mylibrarykotlin.common

import android.app.Application
import com.imyyq.mvvm.base.BaseViewModel
import com.mylibrarykotlin.data.Repository

class NoViewModel(app: Application) : BaseViewModel<Repository>(app) {
}