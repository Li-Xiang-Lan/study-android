package com.demo.study.util

import android.content.Context
import android.content.SharedPreferences
import com.demo.study.MyApp
import com.demo.study.bean.UserInfo
import com.google.gson.Gson


/**
 * sp帮助类
 * Created by cheng on 2018/9/11.
 */

class SPUtil private constructor() {
    private val userinfo: SharedPreferences

    init {
        userinfo= MyApp.context!!.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
    }

    companion object {
        @Volatile
        private var instance: SPUtil? = null
        fun getInstance(): SPUtil? {
            if (null == instance) {
                synchronized(SPUtil::class.java) {
                    if (null == instance) {
                        instance = SPUtil()
                    }
                }
            }
            return instance
        }
    }

    fun saveUserInfo(userInfo: UserInfo?){
        userinfo.edit().putString("userinfo", Gson().toJson(userInfo)).apply()
    }

    fun getUserInfo(): UserInfo? {
        try {
            return Gson().fromJson(userinfo.getString("userinfo",""),UserInfo::class.java)
        } catch (e: Exception) {
            return null
        }
    }

    fun clearUserInfo(){
        userinfo.edit().clear().apply()
    }
}
