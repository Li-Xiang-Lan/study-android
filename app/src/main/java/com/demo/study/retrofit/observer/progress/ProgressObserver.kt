package com.demo.study.retrofit.observer.progress

import android.content.Context
import com.demo.study.retrofit.ObserverOnNextListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by cheng on 2019/6/17.
 */
class ProgressObserver<T> (private val context: Context?, private var listener: ObserverOnNextListener<T>) : Observer<T>,
    ProgressDialogHandler.ProgressCancelListener {
    private var mProgressDialogHandler :ProgressDialogHandler ?=null
    private var d:Disposable?=null

    init {
        mProgressDialogHandler=ProgressDialogHandler(context,this)
    }

    override fun onComplete() {
        dismissProgressDialog()
    }

    override fun onNext(t: T) {
        dismissProgressDialog()
        listener.onNext(t)
    }

    override fun onError(e: Throwable) {
        dismissProgressDialog()
        listener.onError(e)
    }

    override fun onSubscribe(d: Disposable) {
        this.d=d
        showProgressDialog()
    }

    private fun showProgressDialog() {
        mProgressDialogHandler?.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)?.sendToTarget()
//        mProgressDialogHandler?.sendEmptyMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
    }

    private fun dismissProgressDialog() {
        mProgressDialogHandler?.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)?.sendToTarget()
        mProgressDialogHandler=null
    }

    override fun onCancelProgress() {
        dispose()
    }

    fun isDisposed(): Boolean {
        return d == null || d!!.isDisposed()
    }

    fun dispose() {
        if (!isDisposed())
            d?.dispose()
        dismissProgressDialog()
    }
}