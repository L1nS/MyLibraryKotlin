package com.qiwuzhi.mylibrary.utils.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import com.imyyq.mvvm.app.BaseApp
import com.qiwuzhi.mylibrary.utils.FileUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapOptionsUtil {

    fun calculateInSampleSize(
        options: BitmapFactory.Options, reqWidth: Int,
        reqHeight: Int
    ): Int {
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1
        if (width > reqWidth || height > reqHeight) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while (halfWidth / inSampleSize > reqWidth
                && halfHeight / inSampleSize > reqHeight
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    //缩放图片。
    fun createScaleBitmap(
        bmp: Bitmap, reqHeight: Int,
        widthScale: Double
    ): Bitmap? {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，
        // 所以直接设置为false
        val bitmap = Bitmap.createScaledBitmap(
            bmp,
            (reqHeight * widthScale).toInt(), reqHeight, false
        )
        if (bmp != bitmap) { // 如果没有缩放，那么不回收
            bmp.recycle()
        }
        return bitmap
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    fun readPictureDegree(path: String?): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    fun rotatingImageView(angle: Int, bitmap: Bitmap): Bitmap? {
        var returnBm: Bitmap? = null
        // 根据旋转角度，生成旋转矩阵
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: OutOfMemoryError) {
        }
        if (returnBm == null) {
            returnBm = bitmap
        }
        if (bitmap != returnBm) {
            bitmap.recycle()
        }
        return returnBm
    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String): String {

        //原文件
        val oldFile = File(filePath)

        //压缩文件路径 照片路径/
        val appDir = BaseApp.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (appDir != null) {
            if (!appDir.exists()) {
                appDir.mkdir()
            }
        } else {
            return filePath
        }
        val newFileName: String = FileUtil.getFileNameWithSuffix(oldFile.path)
        val quality = 50 //压缩比例0-100
        val degree = readPictureDegree(filePath) //获取相片拍摄角度
        var bm = getSmallBitmap(filePath) //获取一定尺寸的图片
        if (bm != null) {
            if (degree != 0) { //旋转照片角度，防止头像横着显示
                bm = rotatingImageView(degree, bm)
            }
            val outputFile = File(appDir, newFileName)
            try {
                if (!outputFile.exists()) {
                    outputFile.parentFile.mkdirs()
                } else {
                    outputFile.delete()
                }
                val out = FileOutputStream(outputFile)
                bm!!.compress(Bitmap.CompressFormat.JPEG, quality, out)
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return filePath
            }
            return outputFile.path
        } else {
            return filePath
        }
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    private fun getSmallBitmap(filePath: String?): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options)
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800)
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }
}