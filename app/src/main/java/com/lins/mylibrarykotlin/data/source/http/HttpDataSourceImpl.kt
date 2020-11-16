package com.lins.mylibrarykotlin.data.source.http

import com.google.gson.Gson
import com.imyyq.mvvm.http.HttpRequest
import com.lins.mylibrarykotlin.data.source.HttpDataSource
import com.lins.mylibrarykotlin.data.source.http.service.ApiService

object HttpDataSourceImpl : HttpDataSource {
    private var apiService = HttpRequest.getService(ApiService::class.java)


}