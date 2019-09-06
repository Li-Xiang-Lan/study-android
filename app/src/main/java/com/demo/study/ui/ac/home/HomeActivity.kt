package com.demo.study.ui.ac.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.adapter.MineAdapter
import com.demo.study.adapter.ViewPagerAdapter
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.CourseTyepBean
import com.demo.study.bean.EventBusBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.ui.ac.mine.login.LoginActivity
import com.demo.study.ui.ac.mine.userinfo.UserInfoActivity
import com.demo.study.ui.ac.mine.wallet.wallet.WalletActivity
import com.demo.study.ui.frag.course.CourseFragment
import com.demo.study.util.CodeUtil
import com.demo.study.util.SPUtil
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.course_fragment_layout.*
import kotlinx.android.synthetic.main.drawer_left.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by cheng on 2019/8/30.
 */
class HomeActivity: MvpBaseActivity<HomePresenter>(),HomeContract.HomeView, IClickItemListener<Int> {

    override fun initPresenter(): HomePresenter = HomePresenter()

    override fun setTitleId(): Int = 0

    override fun setContentId(): Int = R.layout.home_layout

    override fun titleString(): String = ""

    override fun initEventBus(): Boolean = true

    override fun isImmersionBarEnabled(): Boolean = false

    override fun initView() {
        super.initView()
        val immersionBar = ImmersionBar.with(this)
        immersionBar.titleBar(top_view).statusBarColor(R.color.colore75).navigationBarEnable(false).init()
        presenter?.getCourseType()
        setUserInfo()
        setAdapter()
    }

    override fun getCourseTypeSuccess(arrayList: ArrayList<CourseTyepBean>) {
        initFragment(arrayList)
    }

    private fun initFragment(list: ArrayList<CourseTyepBean>) {
        val titleList= arrayListOf<String>()
        val fragmentList= arrayListOf<Fragment>()
        list.forEach {
            titleList.add(it.title)
            val fragment = CourseFragment()
            val bundle = Bundle()
            bundle.putInt("id",it.categoryId)
            bundle.putString("type",it.title)
            fragment.arguments=bundle
            fragmentList.add(fragment)
        }
        viewPager.adapter= ViewPagerAdapter(supportFragmentManager, fragmentList, titleList)
        tablayout.setViewPager(viewPager)
    }

    override fun setListener() {
        super.setListener()
        navigation_view.setOnClickListener { }
        head_img.setOnClickListener {  drawer.openDrawer(Gravity.START) }
        login_layout.setOnClickListener {
            if (loginOrNot()) startActivity(Intent(this,UserInfoActivity::class.java))
            else startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun setUserInfo() {
        //已登录
        if (loginOrNot()) {
            val userInfo = SPUtil.getInstance()?.getUserInfo()
            name_text.text=userInfo?.userName
            Glide.with(this).load(userInfo?.headUrl).apply(RequestOptions.circleCropTransform().error(R.drawable.logo)).into(head_img)
            Glide.with(this).load(userInfo?.headUrl).apply(RequestOptions.circleCropTransform().error(R.drawable.logo)).into(left_head_img)
        }else{
            name_text.text="点击登录"
            Glide.with(this).clear(head_img)
            Glide.with(this).clear(left_head_img)
        }
    }

    private fun setAdapter() {
        recyclerView.apply {
            layoutManager= LinearLayoutManager(this@HomeActivity)
            adapter=MineAdapter(this@HomeActivity,this@HomeActivity)
        }
    }

    override fun clickItem(t: Int) {
        if (loginOrNot("请先登录再使用此功能")) startActivity(Intent(this,WalletActivity::class.java))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceiveMsg(eventBusBean: EventBusBean){
        when(eventBusBean.code){
            //保存用户信息成功 登录成功 退出登录成功
            CodeUtil.LOGIN_SUCCESS, CodeUtil.LOGOUT_SUCCESS, CodeUtil.SAVE_USERINFO_SUCCESS-> setUserInfo()
        }
    }
}