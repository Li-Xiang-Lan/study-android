package com.demo.study.ui.ac.course.coursedetail

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.CourseBean

/**
 * Created by cheng on 2019/8/30.
 */
interface CourseDetailContract {
    interface CourseDetailView:BaseContract.BaseView{
        fun getMenuId():Int?
        fun getCourseDetailSuccess(courseBean: CourseBean)
        fun sendCommentSuccess()
    }

    interface CourseDetailPresenter:BaseContract.BasePresenter{
        fun getCourseDeatil()
        fun sendComment(comment:String)
    }
}