package com.qiwuzhi.mylibrary.widget.banner

import com.qiwuzhi.mylibrary.widget.banner.bean.SimpleBannerInfo

data class BannerEntity(val imagePath: String) : SimpleBannerInfo() {
    override val xBannerUrl: Any?
        get() = imagePath
}