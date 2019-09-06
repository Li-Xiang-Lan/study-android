package com.demo.study.bean

import java.io.Serializable

/**
 * Created by cheng on 2019/8/30.
 */
data class CourseBean(
    var categoryId: Int,
    var courseCount: Int,
    var coverUrl: String,
    var createTime: Long,
    var id: Int,
    var originalPrice: Int,
    var price: Int,
    var studyTimes: Int,
    var title: String,
    var isBuy:Int
):Serializable