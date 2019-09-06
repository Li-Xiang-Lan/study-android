package com.demo.study.ui.ac.mine.login

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.CodeBean
import com.demo.study.bean.UserInfo
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import com.demo.study.retrofit.observer.progress.ProgressObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/9/3.
 */
class LoginPresenter:BasePresenter<LoginContract.LoginView>(),LoginContract.LoginPresenter {

    override fun getCode(phone: String) {
        val jsonObject = JSONObject()
        jsonObject.put("phone",phone)
        ApiController.service
            .getSmsCode(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(CodeBean::class.java))
            .subscribe(CommonObserver(object: ObserverOnNextListener<CodeBean> {
                override fun onNext(t: CodeBean) {
                    view?.getCodeSuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.getCodeFail()
                }
            }))
    }

    override fun login(phone: String, code: String) {
        val jsonObject = JSONObject()
        jsonObject.put("phone",phone)
        jsonObject.put("code",code)
        ApiController.service
            .login(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(UserInfo::class.java))
            .subscribe(ProgressObserver(view?.getcontext(),object: ObserverOnNextListener<UserInfo> {
                override fun onNext(t: UserInfo) {
                    view?.loginSuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}