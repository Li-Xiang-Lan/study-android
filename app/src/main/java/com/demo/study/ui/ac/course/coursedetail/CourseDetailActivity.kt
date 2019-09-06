package com.demo.study.ui.ac.course.coursedetail

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.adapter.ViewPagerAdapter
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.CourseBean
import com.demo.study.bean.EventBusBean
import com.demo.study.ui.ac.course.buycourse.BuyCourseActivity
import com.demo.study.ui.frag.coursedetail.CourseCatalogFragment
import com.demo.study.ui.frag.coursedetail.CourseCommentFragment
import com.demo.study.util.CodeUtil
import com.demo.study.util.Util
import com.demo.study.view.InputTextMsgDialog
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.course_detail_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by cheng on 2019/8/30.
 */
class CourseDetailActivity: MvpBaseActivity<CourseDetailPresenter>(),CourseDetailContract.CourseDetailView{
    private var isBuy=0
    private var courseBean:CourseBean?=null

    override fun initPresenter(): CourseDetailPresenter = CourseDetailPresenter()

    override fun setTitleId(): Int = 0

    override fun setContentId(): Int = R.layout.course_detail_layout

    override fun titleString(): String = ""

    override fun initEventBus(): Boolean = true

    override fun isImmersionBarEnabled(): Boolean = false

    override fun initView() {
        super.initView()
        val immersionBar = ImmersionBar.with(this)
        immersionBar.titleBar(toolbar).statusBarDarkFont(true, 0.2f).navigationBarEnable(false).init()
        presenter?.getCourseDeatil()
    }

    override fun getMenuId(): Int? = intent.getIntExtra("menuId",0)

    override fun getCourseDetailSuccess(courseBean: CourseBean) {
        this.courseBean=courseBean
        setBaseInfo(courseBean)
        initAppBarLayout()
        initFragment()
    }

    override fun sendCommentSuccess() {
        t("发表评论成功")
        EventBus.getDefault().post(EventBusBean(CodeUtil.SEND_COMMENT_SUCCESS))
    }

    private fun initFragment() {
        val fragmentList= arrayListOf<Fragment>()
        val bundle = Bundle()
        bundle.putInt("menuId",getMenuId()!!)
        bundle.putInt("isBuy",isBuy)
        val catalogFragment = CourseCatalogFragment()
        catalogFragment.arguments=bundle
        fragmentList.add(catalogFragment)
        val commentFragment = CourseCommentFragment()
        commentFragment.arguments=bundle
        fragmentList.add(commentFragment)
        viewPager.adapter= ViewPagerAdapter(supportFragmentManager, fragmentList,arrayOf("课程目录", "课程评论").toList())
        tablayout.setViewPager(viewPager)
    }

    private fun initAppBarLayout() {
        appbar.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->
            val percent = java.lang.Float.valueOf(Math.abs(verticalOffset).toFloat()) / java.lang.Float.valueOf(appBarLayout.totalScrollRange.toFloat())
            val toolbarHeight = appBarLayout.totalScrollRange
            val dy = Math.abs(verticalOffset)
            if (dy <= toolbarHeight) {
                val scale = dy.toFloat() / toolbarHeight
                val alpha = scale * 250
                back_img.setImageResource(if (alpha.toInt() > 100) R.drawable.left_black else R.drawable.left_white)
                toolbar.setBackgroundColor(Color.argb(alpha.toInt(), 250, 250, 250))
                top_title_text.alpha = percent
            }
        })
    }

    private fun setBaseInfo(bean: CourseBean) {
        //0-未购买 1-已购买
        isBuy=bean.isBuy
        buy_text.text=if (isBuy==0) "立即购买" else "立即观看"
        top_title_text.setText(bean.title)
        title_text.text=bean.title
        new_price_text.text="${Util.fenToYuan(bean.price)}币"
        if (bean.originalPrice==0){
            old_price_text.visibility= View.GONE
        }else{
            old_price_text.visibility= View.VISIBLE
            old_price_text.paint.isAntiAlias=true
            old_price_text.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG
            old_price_text.text="${Util.fenToYuan(bean.originalPrice)}币"
        }
        study_num_text.text=bean.studyTimes.toString()+"人已学"
        Glide.with(this).load(bean.coverUrl).apply(RequestOptions.centerCropTransform()).into(bg_img)
    }

    override fun setListener() {
        super.setListener()
        back_img.setOnClickListener { finish() }
        send_comment_text.setOnClickListener {
            val dialog = InputTextMsgDialog(this, R.style.dialog_center)
            dialog.setmOnTextSendListener {
                presenter?.sendComment(it)
            }
            dialog.show()
        }

        buy_text.setOnClickListener {
            EventBus.getDefault().post(EventBusBean(CodeUtil.TO_PLAY_VIDEO))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceiveMsg(eventBusBean: EventBusBean){
        when(eventBusBean.code){
            //课程购买成功
            CodeUtil.BUY_COURSE_SUCCESS-> {
                isBuy=1
                buy_text.text=if (isBuy==0) "立即购买" else "立即观看"
            }
            CodeUtil.TO_BUY_COURSE->{
                val intent = Intent(this, BuyCourseActivity::class.java)
                intent.putExtra("bean",courseBean)
                startActivity(intent)
            }
        }
    }
}