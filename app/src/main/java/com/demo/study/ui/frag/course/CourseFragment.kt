package com.demo.study.ui.frag.course

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.demo.study.R
import com.demo.study.adapter.CourseAdapter
import com.demo.study.base.MvpBaseFragment
import com.demo.study.bean.CourseBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.ui.ac.course.coursedetail.CourseDetailActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.recyclerview_content_layout.*

/**
 * Created by cheng on 2019/8/30.
 */
class CourseFragment :MvpBaseFragment<CoursePresenter>(), CourseContract.CourseView, IClickItemListener<CourseBean>, OnRefreshLoadMoreListener {

    private val courseList= arrayListOf<CourseBean>()
    private val courseAdapter by lazy { CourseAdapter(context!!,arguments?.getString("type"),courseList,this) }
    override fun initPresenter(): CoursePresenter = CoursePresenter()

    override fun contentLayoutId(): Int = R.layout.recyclerview_content_layout

    override fun initView() {
        super.initView()
        setAdapter()
        presenter?.getCourseList(page)
    }

    override fun showMsg(e: Throwable) {
        super.showMsg(e)
        finishRefresh(refreshLayout,false)
    }

    override fun getCategoryId(): Int? = arguments?.getInt("id")

    override fun getCourseListSuccess(page: Int, arrayList: ArrayList<CourseBean>) {
        finishRefresh(refreshLayout,true)
        if (page!=1&&arrayList.size==0) refreshLayout.setNoMoreData(true)
        else{
            this.page=page
            if (page==1) courseList.clear()
            courseList.addAll(arrayList)
            courseAdapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=courseAdapter
        }
    }

    override fun clickItem(t: CourseBean) {
        val intent = Intent(context, CourseDetailActivity::class.java)
        intent.putExtra("menuId",t.id)
        startActivity(intent)
    }

    override fun setListener() {
        super.setListener()
        refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout?) {
        presenter?.getCourseList(page+1)
    }

    override fun onRefresh(refresh: RefreshLayout?) {
        refreshLayout.setNoMoreData(false)
        presenter?.getCourseList(1)
    }
}