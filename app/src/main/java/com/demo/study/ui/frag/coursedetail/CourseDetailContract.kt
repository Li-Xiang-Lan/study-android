package com.demo.study.ui.frag.coursedetail

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.CourseCatalogBean
import com.demo.study.bean.CourseCommentBean

/**
 * Created by cheng on 2019/8/30.
 */
interface CourseDetailContract {
    interface CourseDetailView:BaseContract.BaseView{
        fun getMenuId():Int?
        fun getCourseCatalogSuccess(list: ArrayList<CourseCatalogBean>)
        fun getCourseCommentSuccess(page: Int, list: ArrayList<CourseCommentBean>)
    }

    interface CourseDetailPresenter:BaseContract.BasePresenter{
        fun getCourseCatalog()
        fun getCourseComment(page:Int)
    }
}