package com.lins.mylibrarykotlin.dimens.utils

import com.lins.mylibrarykotlin.dimens.constants.DimenTypes
import com.orhanobut.logger.Logger
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.StringBuilder
import java.math.BigDecimal

object MakeUtils {
    private val XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
    private val XML_RESOURCE_START = "<resources>\r\n"
    private val XML_RESOURCE_END = "</resources>\r\n"
    private val XML_DIMEN_TEMPLETE = "<dimen name=\"qb_%1\$spx_%2\$d\">%3$.2fdp</dimen>\r\n"
    private val XML_BASE_DPI = "<dimen name=\"base_dpi\">%ddp</dimen>\r\n"
    private val MAX_SIZE = 720

    /**
     * 生成的文件名
     */
    private val XML_NAME = "dimens.xml"

    private fun px2dip(pxValue: Float, sw: Int, designWidth: Int): Float {
        val dpValue = (pxValue / (designWidth)) * sw
        val bigDecimal = BigDecimal(dpValue.toDouble())
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    /**
     * 生成所有的尺寸数据
     *
     * @param type
     * @return
     */
    private fun makeAllDimens(type: DimenTypes, designWidth: Int): String {
        var dpValue: Float
        var temp: String
        var sb = StringBuilder()
        try {
            sb.append(XML_HEADER)
            sb.append(XML_RESOURCE_START)
            //备份生成的相关信息
            temp = String.format(XML_BASE_DPI, type.getSwWidthDp())
            sb.append(temp)
            for (i in 0..MAX_SIZE) {
                dpValue = px2dip(i.toFloat(), type.getSwWidthDp(), designWidth)
                temp = String.format(XML_DIMEN_TEMPLETE, "", i, dpValue)
                sb.append(temp)
            }
            sb.append(XML_RESOURCE_END)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }


    /**
     * 生成的目标文件夹
     * 只需传宽进来就行
     *
     * @param type     枚举类型
     * @param buildDir 生成的目标文件夹
     */
     fun makeAll(designWidth: Int, type: DimenTypes, buildDir: String) {
        try {
            //生成规则
            var folderName: String
            if (type.getSwWidthDp() > 0) {
                //适配Android 3.2+
                folderName = "values-sw" + type.getSwWidthDp() + "dp"
            } else {
                return
            }
            //在mac下生成目标目录
//            val home = System.getProperty("user.home")
//            val file = File(
//                home + File.separator + "Desktop" + File.separator + buildDir +
//                        File.separator + folderName)

            //windows下生成目标目录
            val file = File(buildDir + File.separator + folderName)
            if (!file.exists())
                file.mkdirs()
            //生成values文件
            val fos = FileOutputStream(file.absolutePath + File.separator + XML_NAME)
            println(file.absolutePath + File.separator + XML_NAME)
            fos.write(makeAllDimens(type, designWidth).toByteArray())
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}