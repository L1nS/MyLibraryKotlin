package com.lins.mylibrarykotlin.dimens.generator

import com.lins.mylibrarykotlin.dimens.constants.DimenTypes
import com.lins.mylibrarykotlin.dimens.utils.MakeUtils

class DimenGenerator {

    companion object {

        /**
         * 设计稿尺寸(根据自己设计师的设计稿的宽度填入)
         */
        val DESIGN_WIDTH = 750

        /**
         * 设计稿高度  没用到
         */
        private val DESIGN_HEIGHT = 1920


        @JvmStatic
        fun main(args: Array<String>) {
            //在.idea->gradle.xml的<GradleProjectSettings>下一行添加<option name="delegatedBuild" value="false" />
            val values = DimenTypes.values()
            for (value in values) {
                MakeUtils.makeAll(DESIGN_WIDTH, value, "/android/ui/$DESIGN_WIDTH/adapter")
            }
        }
    }

}