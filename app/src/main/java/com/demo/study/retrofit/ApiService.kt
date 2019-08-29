package com.demo.study.retrofit


import com.demo.study.bean.CourseTyepBean
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by cheng on 2019/3/28.
 */
interface ApiService {
//    //获取web url
//    @POST(ApiConstants.GET_WEB_URL)
//    fun getWebUrl(@Body body: RequestBody) : Observable<ResponseModel<WebUrlBean>>

    //获取课程类型
    @GET(ApiConstants.GET_COURSE_TYPE)
    fun getCourseType(): Observable<ResponseModel<List<CourseTyepBean>>>
}