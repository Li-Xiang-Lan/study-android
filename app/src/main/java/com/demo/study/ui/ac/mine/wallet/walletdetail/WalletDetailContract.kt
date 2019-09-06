package com.demo.study.ui.ac.mine.wallet.walletdetail

import com.demo.study.base.basemvp.BaseContract
import com.demo.study.bean.WalletDetailBean

/**
 * Created by cheng on 2019/9/6.
 */
interface WalletDetailContract {
    interface WalletDetailView:BaseContract.BaseView{
        fun getListSuccess(page:Int,list: ArrayList<WalletDetailBean>)
    }

    interface WalletDetailPresenter:BaseContract.BasePresenter{
        fun getList(page:Int)
    }
}