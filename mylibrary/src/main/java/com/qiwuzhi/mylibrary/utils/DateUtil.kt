package com.qiwuzhi.mylibrary.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 杨永青 on 16/9/14.
 */
object DateUtil {
    private const val YMDHM_ = "yyyy-MM-dd HH:mm"
    const val HM = "HH:mm"
    const val YMDHMS_ = "yyyy-MM-dd HH:mm:ss"
    const val YMDHMS_SSS = "yyyy-MM-dd HH:mm:ss.SSS"
    const val YMD_ = "yyyy-MM-dd"

    fun formatYMDHMS_(): String {
        return SimpleDateFormat(YMDHMS_, Locale.getDefault()).format(Date())
    }

    fun formatYMDHMS_SSS(): String {
        return SimpleDateFormat(YMDHMS_SSS, Locale.getDefault()).format(Date())
    }

    fun formatYMDHM_(): String {
        return SimpleDateFormat(YMDHM_, Locale.getDefault()).format(Date())
    }

    fun parseYMD(date: String, addDay: Int): String? {
        try {
            val cal = Calendar.getInstance()
            cal.time = SimpleDateFormat(YMD_, Locale.getDefault()).parse(date)!!
            cal.add(Calendar.DAY_OF_MONTH, addDay)
            return formatYMD_(cal.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * @param dateStart 2019-02-14 16:13:06
     * @param dateEnd 2019-03-14 16:19:29
     * @return [28,0,6]
     */
    fun parseYMDHMS_ToDHM(dateStart: String, dateEnd: String): IntArray? {
        val df = SimpleDateFormat(YMDHMS_, Locale.getDefault())
        try {
            val start = df.parse(dateStart)!!
            val end = df.parse(dateEnd)!!
            val diff = end.time - start.time//这样得到的差值是微秒级别
            val days = (diff / (1000 * 60 * 60 * 24)).toInt()

            val hours = ((diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).toInt()
            val minutes =
                ((diff - (days * (1000 * 60 * 60 * 24)).toLong() - (hours * (1000 * 60 * 60)).toLong()) / (1000 * 60)).toInt()
            //  System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
            val arr = IntArray(3)
            arr[0] = days
            arr[1] = hours
            arr[2] = minutes
            return arr
        } catch (ignored: Exception) {
        }

        return null
    }

    fun parseYMDHMS_ToHMS(dateStart: String, dateEnd: String): String {
        return getHhMmSs(parseYMDHMS_ToMilliseconds(dateStart, dateEnd))
    }

    fun parseYMDHMS_ToMilliseconds(dateStart: String, dateEnd: String): Long {
        val df = SimpleDateFormat(YMDHMS_, Locale.getDefault())
        try {
            val start = df.parse(dateStart)!!
            val end = df.parse(dateEnd)!!
            return end.time - start.time//这样得到的差值是微秒级别
        } catch (ignored: Exception) {
        }

        return 0
    }

    fun getHhMmSs(diff: Long): String {
        val hour = diff / (60 * 1000 * 60)
        val min = diff / (60 * 1000) - hour * 60
        val sec = diff / 1000 - hour * 60 * 60 - min * 60
        return "$hour:" + (if (min < 10)
            "0$min"
        else
            min) + ":" + if (sec < 10)
            "0$sec"
        else
            sec
    }

    fun getHhMmSs_cn(diff: Long): String {
        val hour = diff / (60 * 1000 * 60)
        val min = diff / (60 * 1000) - hour * 60
        val sec = diff / 1000 - hour * 60 * 60 - min * 60
        return "${hour}时" + (if (min < 10)
            "0$min"
        else
            min) + "分" + if (sec < 10)
            "0${sec}秒"
        else
            "${sec}秒"
    }

    fun getDdHhMmSs(diff: Long): String {
        var hour = diff / (60 * 1000 * 60)
        val min = diff / (60 * 1000) - hour * 60
        val sec = diff / 1000 - hour * 60 * 60 - min * 60
        val day = hour / 24
        hour %= 24
        return if (day == 0L)
            ""
        else
            day.toString() + "天" + (if (hour < 10)
                "0$hour"
            else
                hour) + ":" + (if (min < 10)
                "0$min"
            else
                min) + ":" + if (sec < 10)
                "0$sec"
            else
                sec
    }

    fun parseYMDHMS_FrontZero(source: String, field: Int, targetLen: Int): String? {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = SimpleDateFormat(YMDHM_, Locale.getDefault()).parse(source)!!
            val a = calendar.get(field)
            val str = a.toString()
            val len = str.length
            if (len < targetLen) {
                val builder = StringBuilder()
                for (i in 0 until targetLen - len) {
                    builder.append(0)
                }
                builder.append(a)
                return builder.toString()
            }
            return str
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun formatYMDHMS(): String {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
    }

    fun formatMDHM(): String {
        return SimpleDateFormat("MM/dd HH:mm", Locale.getDefault()).format(Date())
    }

    fun formatMDHM(time: Long): String {
        return SimpleDateFormat("MM/dd HH:mm", Locale.getDefault()).format(Date(time))
    }

    fun formatYMD_CN(date: Date): String {
        return SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(date)
    }

    fun formatYMD_CN(time: Long): String {
        return SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(Date(time))
    }

    fun formatYMD_(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun formatYMD_(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }

    fun formatYMD_(time: Long): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(time))
    }

    fun formatYMD_HM_CN(date: Date): String {
        return SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault()).format(date)
    }

    fun formatIMG(): String {
        return SimpleDateFormat("'IMG'_yyyyMMddHHmmss'.png'", Locale.getDefault()).format(
            Date()
        )
    }

    fun format(time: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(time))
    }

    fun getCurTimestamp(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    fun getDiffTimestamp(diff: Int): Long {
        val mCalendar = Calendar.getInstance()
        mCalendar.add(Calendar.DATE, diff)
        return mCalendar.time.time
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    fun getStringToTimestamp(dateString: String, pattern: String): Long {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return date.time
    }


    /**
     * 将毫秒转时分秒
     *
     * @param time
     * @return
     */
    fun millisecondTommss(time: Long): String? {
        val totalSeconds = (time / 1000).toInt()
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        ) else String.format("%02d:%02d", minutes, seconds)
    }
}
