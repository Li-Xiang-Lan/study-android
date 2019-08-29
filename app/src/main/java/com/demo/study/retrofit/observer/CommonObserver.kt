package com.demo.study.retrofit.observer


import com.demo.study.retrofit.ObserverOnNextListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by cheng on 2019/3/29.
 */
class CommonObserver<T> (private var listener: ObserverOnNextListener<T>): Observer<T> {
    var d:Disposable?=null
    override fun onNext(t: T) {
        listener.onNext(t)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        listener.onError(e)
    }

    override fun onSubscribe(d: Disposable) {
        this.d=d
    }

    fun isDisposed():Boolean= d == null || d!!.isDisposed

    fun dispose(){
        if (!isDisposed())
            d!!.dispose()
    }
}