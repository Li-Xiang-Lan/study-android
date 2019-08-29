package com.demo.study.retrofit.observer.progress

import android.content.Context
import android.os.Handler
import android.os.Message
import com.demo.study.R


/**
 * Created by cheng on 2019/6/18.
 */
class ProgressDialogHandler(private val context: Context?,private val mProgressCancelListener:ProgressCancelListener) :Handler(){
    private var pd: RxDialog?=null

    companion object {
        val SHOW_PROGRESS_DIALOG:Int=1
        val DISMISS_PROGRESS_DIALOG:Int=2
    }

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        when(msg?.what){
            SHOW_PROGRESS_DIALOG->initProgressDialog()
            DISMISS_PROGRESS_DIALOG->dismissProgressDialog()
        }
    }

    private fun dismissProgressDialog() {
        pd?.dismiss()
        pd=null
    }

    private fun initProgressDialog() {
        if (null==pd){
            pd= RxDialog(context, R.style.rx_load_dialog).apply {
                setCancelable(true)
                setCanceledOnTouchOutside(false)
                setOnCancelListener { mProgressCancelListener.onCancelProgress() }
            }
        }
        if (!(pd!!.isShowing)){
            pd!!.show()
        }
    }

    interface ProgressCancelListener{
        fun onCancelProgress()
    }
}