package com.demo.study.ui.ac.course.buycourse

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.WalletMoneyBean

/**
 * Created by cheng on 2019/9/4.
 */
interface BuyCourseContract {
    interface BuyCourseView:BaseContract.BaseView{
        fun getUserMoneySuccess(walletMoneyBean: WalletMoneyBean)
        fun buyCourseSuccess()
    }
    interface BuyCoursePresenter:BaseContract.BasePresenter{
        fun getUserMoney()
        fun buyCourse(menuId:Int?)
    }
}