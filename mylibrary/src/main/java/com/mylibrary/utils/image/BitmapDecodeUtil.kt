package com.mylibrary.utils.image

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.imyyq.mvvm.utils.LogUtil
import com.mylibrary.utils.MathUtil
import com.mylibrary.utils.image.BitmapOptionsUtil.calculateInSampleSize
import com.mylibrary.utils.image.BitmapOptionsUtil.createScaleBitmap
import java.io.ByteArrayOutputStream

object BitmapDecodeUtil {
    fun decodeBitmapFromRes(res: Resources?, path: Int, reqWidth: Int, reqHeight: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, path, options)
        //计算原图片的宽高比
        val widthScale = widthScaleWithHeight(options)
        options.inSampleSize = calculateInSampleSize(
            options, reqWidth,
            reqHeight
        )
        options.inJustDecodeBounds = false
        //获取缩略图
        val bmp = BitmapFactory.decodeResource(res, path, options)
        //按照控件比例进行缩放
        val resultBmp = createScaleBitmap(bmp, reqHeight, widthScale)
        bmp.recycle()
        return resultBmp
    }

    fun decodeBitmapFromFile(path: String?, reqWidth: Int, reqHeight: Int): Bitmap? {

        // 取得图片旋转角度
        val angle: Int = BitmapOptionsUtil.readPictureDegree(path)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        //计算原图片的宽高比
        val widthScale = widthScaleWithHeight(options)
        options.inSampleSize = calculateInSampleSize(
            options, reqWidth,
            reqHeight
        )
        options.inJustDecodeBounds = false
        //获取缩略图
        val bmp = BitmapFactory.decodeFile(path, options)
        //按照控件比例进行缩放
        val resultBmp = createScaleBitmap(bmp, reqHeight, widthScale)
        bmp.recycle()
        return if (angle == 0) {
            resultBmp
        } else {
            // 修复图片被旋转的角度
            BitmapOptionsUtil.rotatingImageView(angle, resultBmp!!)
        }
    }

    fun widthScaleWithHeight(options: BitmapFactory.Options): Double {
        val width = options.outWidth.toDouble()
        val height = options.outHeight.toDouble()
        return MathUtil.div(width, height, 2)
    }

    fun calculateBitmapSize(bmp: Bitmap) {
        var bmSize = 0
        bmSize += bmp.byteCount // 得到bitmap的大小
        val kb = bmSize / 1024
        val mb = kb / 1024
        LogUtil.d("bitmap size = " + mb + "MB" + "  ," + kb + "KB")
    }

    fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray? {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bmp.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}