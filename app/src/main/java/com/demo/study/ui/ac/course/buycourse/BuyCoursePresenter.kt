package com.demo.study.ui.ac.course.buycourse

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.SimpleResponseEntity
import com.demo.study.bean.WalletMoneyBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import com.demo.study.retrofit.observer.progress.ProgressObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/9/4.
 */
class BuyCoursePresenter:BasePresenter<BuyCourseContract.BuyCourseView>(),BuyCourseContract.BuyCoursePresenter {

    override fun getUserMoney() {
        ApiController.service
            .getWalletMoney()
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(WalletMoneyBean::class.java))
            .subscribe(CommonObserver(object: ObserverOnNextListener<WalletMoneyBean> {
                override fun onNext(t: WalletMoneyBean) {
                    view?.getUserMoneySuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }

    override fun buyCourse(menuId: Int?) {
        val jsonObject = JSONObject()
        jsonObject.put("menuId",menuId)
        ApiController.service
            .buyCourse(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(SimpleResponseEntity::class.java))
            .subscribe(ProgressObserver(view?.getcontext(),object: ObserverOnNextListener<SimpleResponseEntity> {
                override fun onNext(t: SimpleResponseEntity) {
                    view?.buyCourseSuccess()
                }

                override fun onError(e: Throwable) {
                   view?.showMsg(e)
                }
            }))
    }
}