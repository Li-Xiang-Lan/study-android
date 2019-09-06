package com.demo.study.bean

/**
 * Created by cheng on 2019/8/30.
 */
data class CourseCommentBean(
    var content: String,
    var createTime: Long,
    var id: Int,
    var user: User
){
    data class User(
        var headUrl: String,
        var id: Int,
        var userName: String,
        var userSex: Int
    )
}

