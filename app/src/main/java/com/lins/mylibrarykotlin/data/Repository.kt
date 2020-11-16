package com.lins.mylibrarykotlin.data

import com.imyyq.mvvm.base.BaseModel
import com.lins.mylibrarykotlin.data.source.HttpDataSource
import com.lins.mylibrarykotlin.data.source.http.HttpDataSourceImpl

class Repository : BaseModel(), HttpDataSource {
    private var httpDataSource: HttpDataSource = HttpDataSourceImpl
}