package com.mylibrarykotlin.common

object Constants {
    //本地环境
    private const val HTTP_URL_LOCAL = "http://192.168.1.81:8080"
    private const val HTTP_URL_LOCAL_EXTRA_PATH = ""
    private const val LOCAL_H5_URL = "https://share.xiaomiqiu.com/#";

    //线上环境
    private const val HTTP_URL_RELEASE = "https://www.studytour.info"
    private const val HTTP_URL_RELEASE_EXTRA_PATH = "/service"
    private const val RELEASE_H5_URL = "https://share.studytour.info/#";

    const val HTTP_URL = HTTP_URL_LOCAL
    const val HTTP_URL_EXTRA_PATH = HTTP_URL_LOCAL_EXTRA_PATH
    private const val HTTP_H5 = LOCAL_H5_URL

    //SDK
    const val SDK_WE_CHAT = 1
    const val SDK_QQ = 2


    //分页数量
    const val PAGE_COUNT = 10

    //性别
    const val SEX_TYPE_MALE = 1
    const val SEX_TYPE_FEMALE = 0


    const val REQUEST_ACTION = 1

    //退出登录
    const val RESULT_LOGOUT = 1
    const val RESULT_OK = 2
    const val RESULT_FAIL = 3

    //BUS_TAG
    const val BUS_TAG_REFRESH_DATA = 1//退出登录/登录，等情况下，刷新页面数据
    const val BUS_TAG_LOGOUT = 2//退出登录
    const val BUS_TAG_NAV = 3//主页面切换

}