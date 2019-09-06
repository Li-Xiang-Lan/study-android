package com.demo.study.ui.ac.mine.wallet.walletdetail

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.WalletDetailBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/9/6.
 */
class WalletDetailPresenter:BasePresenter<WalletDetailContract.WalletDetailView>(),WalletDetailContract.WalletDetailPresenter {

    override fun getList(page: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("pageNum",page)
        jsonObject.put("pageSize",10)
        jsonObject.put("type",0)
        ApiController.service
            .getRechargeDetail(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<ArrayList<WalletDetailBean>> {
                override fun onNext(t: ArrayList<WalletDetailBean>) {
                    view?.getListSuccess(page,t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}