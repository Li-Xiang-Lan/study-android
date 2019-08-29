package com.demo.study.retrofit

/**
 * Created by cheng on 2019/3/29.
 */
interface ObserverOnNextListener<T> {
    fun onNext(t:T)
    fun onError(e:Throwable)
}