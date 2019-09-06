package com.demo.study.ui.ac.mine.wallet.wallet

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.RechargeListBean
import com.demo.study.bean.WalletMoneyBean

/**
 * Created by cheng on 2019/9/6.
 */
interface WalletContract {
    interface WalletView:BaseContract.BaseView{
        fun getUserMoneySuccess(walletMoneyBean: WalletMoneyBean)
        fun getRechargeListSuccess(list: ArrayList<RechargeListBean>)
        fun rechargeSuccess()
    }
    interface WalletPresenter:BaseContract.BasePresenter{
        fun getUserMoney()
        fun getRechargeList()
        fun recharge(rechargeListBean: RechargeListBean?)
    }
}