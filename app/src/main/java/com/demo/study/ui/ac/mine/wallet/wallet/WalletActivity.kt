package com.demo.study.ui.ac.mine.wallet.wallet

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.adapter.RechargeListAdapter
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.RechargeListBean
import com.demo.study.bean.WalletMoneyBean
import com.demo.study.ui.ac.mine.wallet.walletdetail.WalletDetailActivity
import com.demo.study.util.SPUtil
import com.demo.study.util.Util
import kotlinx.android.synthetic.main.wallet_layout.*

/**
 * Created by cheng on 2019/9/6.
 */
class WalletActivity : MvpBaseActivity<WalletPresenter>(),WalletContract.WalletView{
    private var rechargeAdapter:RechargeListAdapter?=null

    override fun initPresenter(): WalletPresenter = WalletPresenter()

    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun setContentId(): Int = R.layout.wallet_layout

    override fun titleString(): String = "我的钱包"

    override fun initView() {
        super.initView()
        presenter?.getUserMoney()
        presenter?.getRechargeList()
    }

    override fun getUserMoneySuccess(walletMoneyBean: WalletMoneyBean) {
        val userInfo = SPUtil.getInstance()?.getUserInfo()
        user_name_text.text=userInfo?.userName
        money_num_text.text=Util.fenToYuan(walletMoneyBean.money)
        Glide.with(this).load(userInfo?.headUrl).apply(RequestOptions.circleCropTransform()).into(head_img)
    }

    override fun getRechargeListSuccess(list: ArrayList<RechargeListBean>) {
        rechargeAdapter=RechargeListAdapter(this@WalletActivity,list)
        recyclerView.apply {
            layoutManager= GridLayoutManager(this@WalletActivity,3)
            adapter=rechargeAdapter
        }
    }

    override fun rechargeSuccess() {
        t("充值成功")
        presenter?.getUserMoney()
    }

    override fun setListener() {
        super.setListener()
        recharge_text.setOnClickListener {
            presenter?.recharge(rechargeAdapter?.getChooseBean())
        }

        account_detail_text.setOnClickListener { startActivity(Intent(this,WalletDetailActivity::class.java)) }
    }
}