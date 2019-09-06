package com.demo.study.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.demo.study.R
import com.demo.study.interfaces.ISureDialogListener
import com.demo.study.retrofit.observer.progress.RxDialog
import com.demo.study.ui.ac.mine.login.LoginActivity
import com.demo.study.util.SPUtil
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.base_title_layout.*
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

abstract class BaseActivity : AppCompatActivity(){
    protected var page:Int=1
    protected var mImmersionBar:ImmersionBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (isImmersionBarEnabled()) {
            mImmersionBar= ImmersionBar.with(this)
            mImmersionBar?.titleBar(base_title_layout)?.statusBarColor(R.color.colore75)?.navigationBarEnable(false)?.init()
        }

        if (setTitleId()==0) ShowOrHideTitle(false)
        else{
            base_title_layout.addView(layoutInflater.inflate(setTitleId(),null))
            setTitleClick()
        }
        base_content_layout.addView(layoutInflater.inflate(setContentId(),null))
        if (initEventBus()&&!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
        initView()
        getNetworkData()
        setListener()
    }

    protected fun setTitleClick() {
        try {
            base_back_img.setOnClickListener { clickBack() }
        } catch (e: Exception) {
        }

        try {
            base_title_text.text=titleString()
        } catch (e: Exception) {
        }
    }

    protected open fun ShowOrHideTitle(show: Boolean){
        base_title_layout.visibility=if (show) View.VISIBLE else View.GONE
    }

    abstract fun setTitleId():Int
    abstract fun setContentId():Int
    abstract fun titleString():String
    protected open fun isImmersionBarEnabled(): Boolean =true
    protected open fun initEventBus(): Boolean = false
    protected open fun initView(){}
    protected open fun setListener() {}
    //网络请求放在这个方法中
    protected open fun getNetworkData() {}

    protected open fun clickBack(){
        finish()
    }

    protected fun showErrorMsg(e:Throwable){
        if (e is Error){
            t(e.message)
        }else{
            if (e is HttpException||e is ConnectException||e is TimeoutException ||e is SocketTimeoutException){
                t("网络异常，请稍后重试！")
            }else{
                t("访问失败，请稍后重试！")
            }
        }
    }

    protected fun t(string: String?){
        Toast.makeText(this,string, Toast.LENGTH_SHORT).show()
    }

    private var pd: RxDialog? = null
    protected fun showDialog() {
        if (pd == null) {
            pd = RxDialog(this, R.style.rx_load_dialog)
            pd!!.setCancelable(true)
            pd!!.setCanceledOnTouchOutside(false)
        }
        pd?.show()
    }

    protected fun hideDialog() {
        if (null != pd && pd!!.isShowing())
            pd?.dismiss()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            return if (event.x > left && event.x < right
                && event.y > top && event.y < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                false
            } else {
                true
            }
        }
        return false
    }

    /**
     * 显示确定取消弹窗
     */
    fun showSureOrCancelDialog(title:String,content:String,negBtnStr:String,posBtnStr:String,cancel:Boolean,listener: ISureDialogListener?){
        val create = AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(content)
            setCancelable(cancel)
            setPositiveButton(posBtnStr, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener?.dialogSure()
                }
            })
            setNegativeButton(negBtnStr, null)
        }.create()
        create.show()
        create.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.color333))
    }

    protected fun loginOrNot():Boolean{
        //未登录
        if (null== SPUtil.getInstance()?.getUserInfo()){
            return false
        }
        return true
    }
    protected fun loginOrNot(s:String):Boolean{
        if (loginOrNot()){
            return true
        }else{
            t(s)
            startActivity(Intent(this, LoginActivity::class.java))
            return false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pd.let { pd=null }
        mImmersionBar.let { mImmersionBar=null }
        if (initEventBus()&&EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }
}
