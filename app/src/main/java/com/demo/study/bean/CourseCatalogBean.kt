package com.demo.study.bean

import java.io.Serializable

/**
 * Created by cheng on 2019/8/30.
 */
data class CourseCatalogBean(
    var createTime: Long,
    var id: Int,
    var play: Int,
    var times: Int,
    var title: String,
    var videoUrl: String
):Serializable