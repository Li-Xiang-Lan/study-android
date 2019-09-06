package com.demo.study.bean

/**
 * Created by cheng on 2019/9/6.
 */
data class WalletDetailBean(
    var createTime: Long,
    var id: Int,
    var menuId: Int,
    var price: Int,
    var title: String,
    var type: Int,
    var userId: Int
)