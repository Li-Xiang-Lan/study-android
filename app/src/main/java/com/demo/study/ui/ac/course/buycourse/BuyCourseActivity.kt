package com.demo.study.ui.ac.course.buycourse

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.CourseBean
import com.demo.study.bean.EventBusBean
import com.demo.study.bean.WalletMoneyBean
import com.demo.study.util.CodeUtil
import com.demo.study.util.SPUtil
import com.demo.study.util.Util
import kotlinx.android.synthetic.main.buy_course_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by cheng on 2019/9/4.
 */
class BuyCourseActivity:MvpBaseActivity<BuyCoursePresenter>(),BuyCourseContract.BuyCourseView {

    private var detailBean:CourseBean?=null

    override fun initPresenter(): BuyCoursePresenter = BuyCoursePresenter()

    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun setContentId(): Int = R.layout.buy_course_layout

    override fun titleString(): String = "课程购买"

    override fun initView() {
        super.initView()
        presenter?.getUserMoney()
    }

    override fun getUserMoneySuccess(walletMoneyBean: WalletMoneyBean) {
        setUserInfo(walletMoneyBean.money)
        setCourseInfo(walletMoneyBean.money)
    }

    override fun buyCourseSuccess() {
        t("购买成功")
        EventBus.getDefault().post(EventBusBean(CodeUtil.BUY_COURSE_SUCCESS))
        finish()
    }

    private fun setCourseInfo(money: Int) {
        detailBean = intent.getSerializableExtra("bean") as CourseBean
        if (null==detailBean){
            t("获取课程信息错误")
            finish()
            return
        }
        title_text.text=detailBean?.title
        new_price_text.text="${Util.fenToYuan(detailBean!!.price)}币"
        all_price_text.text=new_price_text.text.toString()
        buy_text.text=if (money<detailBean!!.price) "余额不足，请充值" else "立即购买"
        Glide.with(this).load(detailBean?.coverUrl).apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(Util.dp2px(this, 2f))).error(R.drawable.logo)).into(cover_img)
    }

    private fun setUserInfo(money: Int) {
        money_num_text.text= Util.fenToYuan(money)
        val userInfo = SPUtil.getInstance()?.getUserInfo()
        user_name_text.text=userInfo?.userName
        Glide.with(this).load(userInfo?.headUrl).apply(RequestOptions.circleCropTransform().error(R.drawable.logo)).into(head_img)
    }

    override fun setListener() {
        super.setListener()
        buy_text.setOnClickListener {
            if (buy_text.text.toString().equals("立即购买")) presenter?.buyCourse(detailBean?.id)

        }
    }
}