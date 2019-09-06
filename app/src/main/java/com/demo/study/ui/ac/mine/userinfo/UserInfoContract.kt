package com.demo.study.ui.ac.mine.userinfo

import com.demo.study.base.basemvp.BaseContract

/**
 * Created by cheng on 2019/9/4.
 */
interface UserInfoContract {
    interface UserInfoView:BaseContract.BaseView{
        fun saveUserInfoSuccess()
    }
    interface UserInfoPresenter:BaseContract.BasePresenter{
        fun saveUserInfo(name:String,url:String,sex:Int)
    }
}