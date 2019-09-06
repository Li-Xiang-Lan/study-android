package com.demo.study.util

import android.content.Context
import android.os.Environment
import java.security.MessageDigest
import java.text.DecimalFormat


/**
 * Created by cheng on 2019/6/24.
 */
class Util {
    companion object{
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.applicationContext.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 获取版本号
         */
        fun getVersionCode(ctx: Context): Int {
            var localVersion = 0
            try {
                val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.applicationContext.packageName, 0)
                localVersion = packageInfo.versionCode
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return localVersion
        }

        /**
         * 获取版本号名称
         */
        fun getVersionName(ctx: Context): String {
            var localVersion = ""
            try {
                val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.applicationContext.packageName, 0)
                localVersion = packageInfo.versionName
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return localVersion
        }

        /**
         * 获取手机可存储路径
         * @param context 上下文
         * @return 手机可存储路径
         */
        fun getRootPath(context: Context): String {
            try {
                // 是否有SD卡
                return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable()) {
                    context.applicationContext.externalCacheDir!!.path // 有
                } else {
                    context.applicationContext.cacheDir.path // 无
                }
            } catch (e: Exception) {
                return context.applicationContext.cacheDir.path // 无
            }
        }

        /**
         * string转md5
         */
        fun strToMD5(str: String): String {
            try {
                val md5 = MessageDigest.getInstance("MD5")
                md5.update(str.toByteArray(charset("UTF-8")))
                val messageDigest = md5.digest()
                val hexString = StringBuilder()
                for (b in messageDigest) {
                    hexString.append(String.format("%02X", b))
                }
                return hexString.toString().toLowerCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 把分转换成元
         * @param fen
         * @return
         */
        fun fenToYuan(fen: Int): String {
            val df = DecimalFormat("0.00")//格式化小数
            return df.format(fen.toDouble() / 100)
        }

        /**
         * 数字转换成字母
         */
        fun numberToLetter(num: Int): String {
            var num = num
            if (num <= 0) {
                return ""
            }
            var letter = ""
            num--
            do {
                if (letter.length > 0) {
                    num--
                }
                letter = (num % 26 + 'A'.toInt()).toChar() + letter
                num = (num - num % 26) / 26
            } while (num > 0)

            return letter
        }
    }
}