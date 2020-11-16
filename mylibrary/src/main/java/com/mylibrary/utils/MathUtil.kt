package com.mylibrary.utils

import java.math.BigDecimal

object MathUtil {
    /**
     * 除法运算
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留的小数位数
     * @return
     */
    fun div(v1: Double, v2: Double, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        if (v2 == 0.0) return v1
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(v1: Double, v2: Int, scale: Int): String {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        if (v2 == 0) return v1.toString()
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString()
    }
}