package com.demo.study.ui.ac.course.coursedetail

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.CourseBean
import com.demo.study.bean.SimpleResponseEntity
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import com.demo.study.retrofit.observer.progress.ProgressObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/8/30.
 */
class CourseDetailPresenter:BasePresenter<CourseDetailContract.CourseDetailView>(),CourseDetailContract.CourseDetailPresenter {

    override fun getCourseDeatil() {
        val jsonObject = JSONObject()
        jsonObject.put("courseId",view?.getMenuId())
        ApiController.service
            .getCourseDetail(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(CourseBean::class.java))
            .subscribe(CommonObserver(object: ObserverOnNextListener<CourseBean> {
                override fun onNext(t: CourseBean) {
                    view?.getCourseDetailSuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }

    override fun sendComment(comment: String) {
        val jsonObject = JSONObject()
        jsonObject.put("menuId",view?.getMenuId())
        jsonObject.put("content",comment)
        ApiController.service
            .sendCourseComment(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(SimpleResponseEntity::class.java))
            .subscribe(ProgressObserver(view?.getcontext(),object: ObserverOnNextListener<SimpleResponseEntity> {
                override fun onNext(t: SimpleResponseEntity) {
                    view?.sendCommentSuccess()
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}