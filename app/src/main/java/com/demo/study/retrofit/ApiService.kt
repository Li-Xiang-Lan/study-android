package com.demo.study.retrofit


import com.demo.study.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by cheng on 2019/3/28.
 */
interface ApiService {

    /**
     * 课程模块
     */
    //获取课程类型
    @GET(ApiConstants.GET_COURSE_TYPE)
    fun getCourseType(): Observable<ResponseModel<ArrayList<CourseTyepBean>>>
    //获取课程列表
    @POST(ApiConstants.GET_COURSE_LIST)
    fun getCourseList(@Body body: RequestBody) : Observable<ResponseModel<ArrayList<CourseBean>>>
    //获取课程详情
    @POST(ApiConstants.GET_COURSE_DETAIL)
    fun getCourseDetail(@Body body: RequestBody) : Observable<ResponseModel<CourseBean>>
    //获取课程播放目录
    @POST(ApiConstants.GET_COURSE_CATALOG)
    fun getCourseCatalog(@Body body: RequestBody) : Observable<ResponseModel<ArrayList<CourseCatalogBean>>>
    //获取课程评论
    @POST(ApiConstants.GET_COURSE_COMMENT)
    fun getCourseComment(@Body body: RequestBody) : Observable<ResponseModel<ArrayList<CourseCommentBean>>>
    //发表评论
    @POST(ApiConstants.SEND_COURSE_COMMENT)
    fun sendCourseComment(@Body body: RequestBody) : Observable<ResponseModel<SimpleResponseEntity>>
    //购买课程
    @POST(ApiConstants.BUY_COURSE)
    fun buyCourse(@Body body: RequestBody) : Observable<ResponseModel<SimpleResponseEntity>>


    /**
     * 个人模块
     */
    //获取验证码
    @POST(ApiConstants.GET_SMS_CODE)
    fun getSmsCode(@Body body: RequestBody) : Observable<ResponseModel<CodeBean>>
    //登录
    @POST(ApiConstants.LOGIN)
    fun login(@Body body: RequestBody) : Observable<ResponseModel<UserInfo>>
    //修改用户信息
    @POST(ApiConstants.CHANGE_USER_INFO)
    fun changeUserInfo(@Body body: RequestBody) : Observable<ResponseModel<SimpleResponseEntity>>
    //获取钱包金额
    @GET(ApiConstants.GET_WALLET_MONEY)
    fun getWalletMoney(): Observable<ResponseModel<WalletMoneyBean>>
    //获取充值列表
    @GET(ApiConstants.GET_RECHARGE_LIST)
    fun getRechargeList(): Observable<ResponseModel<ArrayList<RechargeListBean>>>
    //充值
    @POST(ApiConstants.RECHARGE)
    fun recharge(@Body body: RequestBody) : Observable<ResponseModel<SimpleResponseEntity>>
    //获取充值记录
    @POST(ApiConstants.GET_RECHARGE_DETAIL)
    fun getRechargeDetail(@Body body: RequestBody) : Observable<ResponseModel<ArrayList<WalletDetailBean>>>
}