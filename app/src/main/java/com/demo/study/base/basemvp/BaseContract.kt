package com.demo.study.base.basemvp

import android.content.Context

/**
 * Created by cheng on 2019/4/4.
 */
interface BaseContract {
    interface BasePresenter{
        fun attachView(view: BaseView)
        fun detachView()
    }

    interface BaseView{
        fun getcontext(): Context?
        fun showMsg(msg: String)
        fun showMsg(e: Throwable)
    }
}