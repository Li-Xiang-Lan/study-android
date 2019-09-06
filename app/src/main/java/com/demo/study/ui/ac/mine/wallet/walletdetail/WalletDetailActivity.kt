package com.demo.study.ui.ac.mine.wallet.walletdetail

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.demo.study.R
import com.demo.study.adapter.WalletDetailAdapter
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.WalletDetailBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.ui.ac.course.coursedetail.CourseDetailActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.recyclerview_content_layout.*

/**
 * Created by cheng on 2019/9/6.
 */
class WalletDetailActivity : MvpBaseActivity<WalletDetailPresenter>(),WalletDetailContract.WalletDetailView, IClickItemListener<WalletDetailBean>,
    OnRefreshLoadMoreListener {

    private val detailList= arrayListOf<WalletDetailBean>()
    private val detailAdapter by lazy { WalletDetailAdapter(this,detailList,this) }

    override fun initPresenter(): WalletDetailPresenter = WalletDetailPresenter()

    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun setContentId(): Int = R.layout.recyclerview_content_layout

    override fun titleString(): String =  "账户明细"

    override fun initView() {
        super.initView()
        setAdapter()
        presenter?.getList(page)
    }

    override fun showMsg(e: Throwable) {
        super.showMsg(e)
        finishRefresh(refreshLayout,false)
    }

    override fun getListSuccess(page: Int, list: ArrayList<WalletDetailBean>) {
        finishRefresh(refreshLayout,true)
        if (page!=1&&list.size==0) refreshLayout.setNoMoreData(true)
        else{
            if (page==1) detailList.clear()
            this.page=page
            detailList.addAll(list)
            detailAdapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        recyclerView.apply {
            layoutManager= LinearLayoutManager(this@WalletDetailActivity)
            adapter=detailAdapter
        }
    }

    override fun clickItem(t: WalletDetailBean) {
        if (t.type==1){
            val intent = Intent(this, CourseDetailActivity::class.java)
            intent.putExtra("menuId",t.menuId)
            startActivity(intent)
        }
    }

    override fun setListener() {
        super.setListener()
        refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout?) {
        presenter?.getList(page+1)
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        this.refreshLayout.setNoMoreData(false)
        presenter?.getList(1)
    }
}