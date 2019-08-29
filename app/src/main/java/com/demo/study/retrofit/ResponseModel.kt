package com.demo.study.retrofit

/**
 * Created by cheng on 2019/3/28.
 */
data class ResponseModel<T>(val status:Int, val errorMsg:String, val data:T)