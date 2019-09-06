package com.demo.study.ui.ac.mine.userinfo

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.SimpleResponseEntity
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.progress.ProgressObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/9/4.
 */
class UserInfoPresenter:BasePresenter<UserInfoContract.UserInfoView>(),UserInfoContract.UserInfoPresenter {

    override fun saveUserInfo(name: String, url: String, sex: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("userName",name)
        jsonObject.put("userSex",sex)
        jsonObject.put("headUrl",url)
        ApiController.service
            .changeUserInfo(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(SimpleResponseEntity::class.java))
            .subscribe(ProgressObserver(view?.getcontext(),object: ObserverOnNextListener<SimpleResponseEntity> {
                override fun onNext(t: SimpleResponseEntity) {
                    view?.saveUserInfoSuccess()
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}