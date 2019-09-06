package com.demo.study.ui.frag.coursedetail

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.CourseCatalogBean
import com.demo.study.bean.CourseCommentBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/8/30.
 */
class CourseDetailPresenter:BasePresenter<CourseDetailContract.CourseDetailView>(),CourseDetailContract.CourseDetailPresenter {
    override fun getCourseCatalog() {
        val jsonObject = JSONObject()
        jsonObject.put("menuId",view?.getMenuId())
        ApiController.service
            .getCourseCatalog(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<ArrayList<CourseCatalogBean>> {
                override fun onNext(t: ArrayList<CourseCatalogBean>) {
                    view?.getCourseCatalogSuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }

    override fun getCourseComment(page: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("menuId",view?.getMenuId())
        jsonObject.put("pageNum",page)
        jsonObject.put("pageSize",10)
        ApiController.service
            .getCourseComment(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<ArrayList<CourseCommentBean>> {
                override fun onNext(t: ArrayList<CourseCommentBean>) {
                    view?.getCourseCommentSuccess(page,t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}