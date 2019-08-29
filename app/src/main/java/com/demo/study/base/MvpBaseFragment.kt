package com.demo.study.base

import android.content.Context
import com.demo.study.base.BaseFragment
import com.demo.study.base.basemvp.BaseContract
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by cheng on 2019/4/2.
 */
abstract class MvpBaseFragment<P : BaseContract.BasePresenter> : BaseFragment() ,BaseContract.BaseView{
    protected var presenter:P?=null
    override fun initView() {
        super.initView()
        presenter=initPresenter()
        presenter?.attachView(this)
    }

    protected abstract fun initPresenter():P

    override fun getcontext(): Context? =context

    override fun showMsg(msg:String) {
        t(msg)
    }

    override fun showMsg(e:Throwable) {
        showErrorMsg(e)
    }

    protected fun finishRefresh(refreshLayout: SmartRefreshLayout,success:Boolean){
        try {
            if (refreshLayout.isRefreshing) refreshLayout.finishRefresh(success)
            if (refreshLayout.isLoading) refreshLayout.finishLoadMore(success)
        }catch (e:Exception){

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }
}