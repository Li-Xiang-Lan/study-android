package com.demo.study.ui.frag.course

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.CourseBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/8/30.
 */
class CoursePresenter:BasePresenter<CourseContract.CourseView>(),CourseContract.CoursePresenter {

    override fun getCourseList(page: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("categoryId",view?.getCategoryId())
        jsonObject.put("pageNum",page)
        jsonObject.put("pageSize",10)
        ApiController.service
            .getCourseList(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<ArrayList<CourseBean>> {
                override fun onNext(t: ArrayList<CourseBean>) {
                    view?.getCourseListSuccess(page, t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}
