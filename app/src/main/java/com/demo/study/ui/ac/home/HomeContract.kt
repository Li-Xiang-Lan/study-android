package com.demo.study.ui.ac.home

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.CourseTyepBean

/**
 * Created by cheng on 2019/9/4.
 */
interface HomeContract {
    interface HomeView:BaseContract.BaseView{
        fun getCourseTypeSuccess(arrayList: ArrayList<CourseTyepBean>)
    }

    interface HomePresenter:BaseContract.BasePresenter{
        fun getCourseType()
    }
}