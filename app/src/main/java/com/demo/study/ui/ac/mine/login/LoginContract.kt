package com.demo.study.ui.ac.mine.login

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.CodeBean
import com.demo.study.bean.UserInfo

/**
 * Created by cheng on 2019/9/3.
 */
interface LoginContract {
    interface LoginView:BaseContract.BaseView{
        fun getCodeSuccess(codeBean: CodeBean)
        fun getCodeFail()
        fun loginSuccess(userInfo: UserInfo)
    }
    interface LoginPresenter:BaseContract.BasePresenter{
        fun getCode(phone:String)
        fun login(phone:String,code:String)
    }
}