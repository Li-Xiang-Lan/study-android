package com.demo.study.ui.frag.coursedetail

import android.support.v7.widget.LinearLayoutManager
import com.demo.study.R
import com.demo.study.adapter.CourseCommentAdapter
import com.demo.study.base.MvpBaseFragment
import com.demo.study.bean.CourseCatalogBean
import com.demo.study.bean.CourseCommentBean
import com.demo.study.bean.EventBusBean
import com.demo.study.util.CodeUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.recyclerview_content_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by cheng on 2019/8/30.
 */
class CourseCommentFragment:MvpBaseFragment<CourseDetailPresenter>(),CourseDetailContract.CourseDetailView, OnRefreshLoadMoreListener {

    private val commentList= arrayListOf<CourseCommentBean>()
    private val commentAdapter by lazy { CourseCommentAdapter(context!!,commentList) }

    override fun initPresenter(): CourseDetailPresenter = CourseDetailPresenter()

    override fun contentLayoutId(): Int = R.layout.recyclerview_content_layout

    override fun initEventBus(): Boolean = true

    override fun initView() {
        super.initView()
        setAdapter()
        presenter?.getCourseComment(page)
    }

    override fun getMenuId(): Int? =arguments?.getInt("menuId",0)

    override fun getCourseCatalogSuccess(list: ArrayList<CourseCatalogBean>) {}

    override fun getCourseCommentSuccess(page: Int, list: ArrayList<CourseCommentBean>) {
        finishRefresh(refreshLayout,true)
        if (page!=1&&list.size==0) refreshLayout.setNoMoreData(true)
        else{
            this.page=page
            if (page==1) commentList.clear()
            commentList.addAll(list)
            commentAdapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=commentAdapter
        }
    }

    override fun setListener() {
        super.setListener()
        refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout?) {
        presenter?.getCourseComment(page+1)
    }

    override fun onRefresh(refresh: RefreshLayout?) {
        refreshLayout.setNoMoreData(false)
        presenter?.getCourseComment(1)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceiveMsg(eventBusBean: EventBusBean){
        when(eventBusBean.code){
            //发表评论成功
            CodeUtil.SEND_COMMENT_SUCCESS-> refreshLayout.autoRefresh()
        }
    }
}