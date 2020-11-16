package com.lins.mylibrarykotlin.dimens.constants

enum class DimenTypes {

    //适配Android 3.2以上   大部分手机的sw值集中在  300-460之间
    DP_sw__300(300),
    DP_sw__320(320),
    DP_sw__340(340),
    DP_sw__360(360),
    DP_sw__380(380),
    DP_sw__400(400),
    DP_sw__420(420),
    DP_sw__440(440),
    DP_sw__460(460);

    private var swWidthDp: Int

    constructor(swWidthDp: Int) {
        this.swWidthDp = swWidthDp
    }

    fun getSwWidthDp():Int=swWidthDp

}