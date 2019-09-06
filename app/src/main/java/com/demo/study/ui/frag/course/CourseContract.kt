package com.demo.study.ui.frag.course

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.CourseBean

/**
 * Created by cheng on 2019/8/30.
 */
interface CourseContract {
    interface CourseView:BaseContract.BaseView{
        fun getCategoryId():Int?
        fun getCourseListSuccess(page: Int,arrayList: ArrayList<CourseBean>)
    }

    interface CoursePresenter:BaseContract.BasePresenter{
        fun getCourseList(page:Int);
    }
}