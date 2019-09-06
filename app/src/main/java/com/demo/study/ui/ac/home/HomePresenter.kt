package com.demo.study.ui.ac.home

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.CourseTyepBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver

/**
 * Created by cheng on 2019/9/4.
 */
class HomePresenter:BasePresenter<HomeContract.HomeView>(),HomeContract.HomePresenter {
    override fun getCourseType() {
        ApiController.service
            .getCourseType()
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<ArrayList<CourseTyepBean>> {
                override fun onNext(t: ArrayList<CourseTyepBean>) {
                    view?.getCourseTypeSuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}