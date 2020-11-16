package com.mylibrarykotlin.data.source.http

import com.imyyq.mvvm.http.HttpRequest
import com.mylibrarykotlin.data.source.HttpDataSource
import com.mylibrarykotlin.data.source.http.service.ApiService

object HttpDataSourceImpl : HttpDataSource {
    private var apiService = HttpRequest.getService(ApiService::class.java)


}