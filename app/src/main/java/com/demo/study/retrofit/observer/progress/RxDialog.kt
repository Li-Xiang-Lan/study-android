package com.demo.study.retrofit.observer.progress

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.demo.study.R


/**
 * Created by cheng on 2019/6/18.
 */
class RxDialog(context: Context?, themeResId: Int) : Dialog(context, themeResId) {
    private var mLayoutParams: WindowManager.LayoutParams?=null
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = this.window
        mLayoutParams = window!!.attributes
        mLayoutParams?.alpha = 1f
        window.attributes = mLayoutParams
        if (mLayoutParams != null) {
            mLayoutParams?.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT
            mLayoutParams?.gravity = Gravity.CENTER
        }

        val mDialogContentView = LayoutInflater.from(context).inflate(R.layout.rx_load_dialog, null)
        setContentView(mDialogContentView)
    }
}