package com.demo.study.retrofit

/**
 * Created by cheng on 2019/3/28.
 */
object ApiConstants {
    const val BASE_URL="http://192.168.0.152:8080/study/"

    /**
     * 课程模块
     */
    //获取课程类型
    const val GET_COURSE_TYPE="course/getcoursecategory"
    //获取课程列表
    const val GET_COURSE_LIST="course/getcourselist"
    //获取课程详情
    const val GET_COURSE_DETAIL="course/getcoursedetail"
    //获取课程播放目录
    const val GET_COURSE_CATALOG="course/getcoursemenulist"
    //获取课程评论
    const val GET_COURSE_COMMENT="course/getcoursecomment"
    //发表评论
    const val SEND_COURSE_COMMENT="course/addcoursecomment.do"
    //购买课程
    const val BUY_COURSE="course/buycourse.do"


    /**
     * 个人模块
     */
    //获取验证码
    const val GET_SMS_CODE="user/getcode"
    //登录
    const val LOGIN="user/login"
    //修改用户信息
    const val CHANGE_USER_INFO="user/updateuserinfo.do"
    //获取钱包金额
    const val GET_WALLET_MONEY="user/getmoney.do"
    //获取充值列表
    const val GET_RECHARGE_LIST="user/getrechargelist.do"
    //充值
    const val RECHARGE="user/updatemoney.do"
    //获取充值记录
    const val GET_RECHARGE_DETAIL="user/getmoneyrecord.do"
}