package com.mylibrarykotlin.common

import com.imyyq.mvvm.base.IBaseResponse

data class BaseEntity<T>(
    var data: T?,
    var code: Int?,
    var msg: String?
) : IBaseResponse<T> {
    override fun code() = code

    override fun msg() = msg

    override fun data() = data

    override fun isSuccess() = code == 200
}