package com.demo.study.ui.ac.mine.wallet.wallet

import com.demo.study.base.basemvp.BasePresenter
import com.demo.study.bean.RechargeListBean
import com.demo.study.bean.SimpleResponseEntity
import com.demo.study.bean.WalletMoneyBean
import com.demo.study.retrofit.ApiController
import com.demo.study.retrofit.ObserverOnNextListener
import com.demo.study.retrofit.RxSchedulers
import com.demo.study.retrofit.observer.CommonObserver
import com.demo.study.retrofit.observer.progress.ProgressObserver
import org.json.JSONObject

/**
 * Created by cheng on 2019/9/6.
 */
class WalletPresenter:BasePresenter<WalletContract.WalletView>(),WalletContract.WalletPresenter {
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

    override fun getRechargeList() {
        ApiController.service
            .getRechargeList()
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeListData())
            .subscribe(CommonObserver(object: ObserverOnNextListener<ArrayList<RechargeListBean>> {
                override fun onNext(t: ArrayList<RechargeListBean>) {
                    view?.getRechargeListSuccess(t)
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }

    override fun recharge(rechargeListBean: RechargeListBean?) {
        if (null==rechargeListBean){
            view?.showMsg("请选择充值金额")
            return
        }
        val jsonObject = JSONObject()
        jsonObject.put("id",rechargeListBean?.id)
        jsonObject.put("money",rechargeListBean?.money)
        ApiController.service
            .recharge(getRequestBody(jsonObject))
            .compose(RxSchedulers.io_main())
            .flatMap(ApiController.judgeData(SimpleResponseEntity::class.java))
            .subscribe(ProgressObserver(view?.getcontext(),object: ObserverOnNextListener<SimpleResponseEntity> {
                override fun onNext(t: SimpleResponseEntity) {
                    view?.rechargeSuccess()
                }

                override fun onError(e: Throwable) {
                    view?.showMsg(e)
                }
            }))
    }
}