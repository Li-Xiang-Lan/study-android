package com.demo.study.ui.frag.coursedetail

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.demo.study.R
import com.demo.study.adapter.CourseCatalogAdapter
import com.demo.study.base.MvpBaseFragment
import com.demo.study.bean.CourseCatalogBean
import com.demo.study.bean.CourseCommentBean
import com.demo.study.bean.EventBusBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.ui.ac.course.playvideo.PlayVideoActivity
import com.demo.study.util.CodeUtil
import kotlinx.android.synthetic.main.recyclerview_content_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by cheng on 2019/8/30.
 */
class CourseCatalogFragment:MvpBaseFragment<CourseDetailPresenter>(),CourseDetailContract.CourseDetailView, IClickItemListener<Int> {
    private var isBuy=0
    private val courseCatalogList= arrayListOf<CourseCatalogBean>()
    private val courseCatalogAdapter by lazy { CourseCatalogAdapter(context!!,courseCatalogList, this) }

    override fun initPresenter(): CourseDetailPresenter = CourseDetailPresenter()

    override fun contentLayoutId(): Int = R.layout.recyclerview_content_layout

    override fun initEventBus(): Boolean = true

    override fun initView() {
        super.initView()
        setAdapter()
        isBuy= arguments?.getInt("isBuy",0)!!
        presenter?.getCourseCatalog()
    }

    override fun showMsg(e: Throwable) {
        super.showMsg(e)
        finishRefresh(refreshLayout,false)
    }

    override fun getMenuId(): Int? =arguments?.getInt("menuId",0)

    override fun getCourseCommentSuccess(page: Int, list: ArrayList<CourseCommentBean>) {}

    override fun getCourseCatalogSuccess(list: ArrayList<CourseCatalogBean>) {
        finishRefresh(refreshLayout,true)
        courseCatalogList.clear()
        courseCatalogList.addAll(list)
        courseCatalogAdapter.notifyDataSetChanged()
    }
    private fun setAdapter(){
        recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=courseCatalogAdapter
        }
    }

    override fun clickItem(t: Int) {
        if (isBuy==0){
            EventBus.getDefault().post(EventBusBean(CodeUtil.TO_BUY_COURSE))
            return
        }
        val intent = Intent(context, PlayVideoActivity::class.java)
        intent.putExtra("list",courseCatalogList)
        intent.putExtra("pos",t)
        startActivity(intent)
    }

    override fun setListener() {
        super.setListener()
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnRefreshListener { presenter?.getCourseCatalog() }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceiveMsg(eventBusBean: EventBusBean){
        when(eventBusBean.code){
            //课程购买成功
            CodeUtil.BUY_COURSE_SUCCESS-> isBuy=1
            //播放视频
            CodeUtil.TO_PLAY_VIDEO-> clickItem(0)
        }
    }
}