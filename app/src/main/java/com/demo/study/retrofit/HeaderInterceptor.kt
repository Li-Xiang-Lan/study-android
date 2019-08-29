package com.demo.study.retrofit

import android.os.Build
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by cheng on 2019/3/29.
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var oldHttpUrl = request.url()
        var builder = request.newBuilder()
        var headerValues = request.headers("bqs_auth")
        if (null!=headerValues&&headerValues.size>0){
            builder.removeHeader("bqs_auth")
            var headerValue=headerValues.get(0)
            var newBaseUrl:HttpUrl?=null
            when(headerValue){
                "wechat"->newBaseUrl= HttpUrl.parse("https://api.weixin.qq.com/")
                else->newBaseUrl=oldHttpUrl
            }
            var newFullUrl = oldHttpUrl.newBuilder()
                    .scheme("https")
                    .host(newBaseUrl!!.host())
                    .port(newBaseUrl.port())
                    .build()
            return chain.proceed(builder.url(newFullUrl).build())
        }

//        val userInfo = SPUtil.getInstance()?.getUserId()
        return chain.proceed(chain.request().newBuilder()
                .header("session", "")
                .header("userId", "")
                .build())
    }
}