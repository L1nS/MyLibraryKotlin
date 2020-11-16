package com.mylibrary.utils

import android.text.TextUtils

object FileUtil {

    /**
     * 获取文件名及后缀
     */
    fun getFileNameWithSuffix(path: String): String {
        if (TextUtils.isEmpty(path)) {
            return ""
        }
        val start = path.lastIndexOf("/")
        return if (start != -1) {
            path.substring(start + 1)
        } else {
            ""
        }
    }
}
