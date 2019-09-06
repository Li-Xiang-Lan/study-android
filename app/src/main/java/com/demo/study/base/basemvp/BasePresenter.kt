package com.demo.study.base.basemvp

import com.demo.study.retrofit.observer.CommonObserver
import okhttp3.RequestBody
import org.json.JSONObject


/**
 * Created by cheng on 2019/4/4.
 */
abstract class BasePresenter<T> : BaseContract.BasePresenter{
    protected var view:T?=null
    protected val list by lazy { arrayListOf<CommonObserver<*>>() }

    override fun detachView() {
        if (null!=list&&list.size!=0){
            list.forEach { it.dispose() }
        }
        list.clear()
        view?.let { view=null }
    }

    override fun attachView(view: BaseContract.BaseView) {
        this.view=view as T
    }

    protected fun addObserver(c: CommonObserver<*>){
        list.add(c)
    }

    protected fun getRequestBody(s: JSONObject): RequestBody =
        RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), s.toString());
}