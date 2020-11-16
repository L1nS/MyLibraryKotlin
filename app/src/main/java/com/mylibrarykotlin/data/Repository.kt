package com.mylibrarykotlin.data

import com.imyyq.mvvm.base.BaseModel
import com.mylibrarykotlin.data.source.HttpDataSource
import com.mylibrarykotlin.data.source.http.HttpDataSourceImpl

class Repository : BaseModel(), HttpDataSource {
    private var httpDataSource: HttpDataSource = HttpDataSourceImpl
}