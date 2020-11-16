package com.mylibrary.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.imyyq.mvvm.app.BaseApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileStoreUtil {

    fun saveImageToGallery(bmp: Bitmap): Boolean {
        val appDir =
            BaseApp.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!appDir!!.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        return try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            val insertImage = MediaStore.Images.Media.insertImage(
                BaseApp.getInstance().contentResolver,
                file.absolutePath, fileName, null
            )
            val file1 = File(getRealPathFromURI(Uri.parse(insertImage)))
            updatePhotoMedia(file1)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    //更新图库
    private fun updatePhotoMedia(file: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
        intent.data = Uri.fromFile(file)
        BaseApp.getInstance().sendBroadcast(intent)
    }

    //得到绝对地址
    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            BaseApp.getInstance().contentResolver.query(contentUri, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val fileStr = cursor.getString(column_index)
        cursor.close()
        return fileStr
    }

    /**
     * 保存图片
     *
     * @param bmp
     * @param fileName
     * @return
     */
    fun saveImage(bmp: Bitmap, fileName: String?): String? {
        // 首先保存图片
        val appDir = BaseApp.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!appDir!!.exists()) {
            appDir.mkdir()
        }
        val file = File(appDir, fileName)
        return try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}