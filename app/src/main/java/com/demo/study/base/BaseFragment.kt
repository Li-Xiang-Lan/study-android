package com.demo.study.base

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.demo.study.R
import com.demo.study.interfaces.ISureDialogListener
import kotlinx.android.synthetic.main.activity_base.*
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

/**
 * Created by cheng on 2019/6/21.
 */
abstract class BaseFragment : Fragment(){

    protected var page:Int=1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_fragment_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        base_content_layout.addView(layoutInflater.inflate(contentLayoutId(),null))
        if (initEventBus()&&!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
        initView()
        getNetworkData()
        setListener()
    }

    abstract fun contentLayoutId():Int

    protected open fun initView(){}

    protected open fun getNetworkData(){}

    protected open fun setListener(){}

    protected open fun initEventBus()=false

//    protected fun loginOrNot():Boolean{
//        //未登录
//        if (null==SPUtil?.getInstance()?.getUserInfo()){
//            return false
//        }
//        return true
//    }
//    protected fun loginOrNot(s:String):Boolean{
//        if (loginOrNot()){
//            return true
//        }else{
//            t(s)
//            startActivity(Intent(context,LoginActivity::class.java))
//            return false
//        }
//    }

    protected fun showErrorMsg(e:Throwable){
        if (e is Error){
            t(e.message)
        }else{
            if (e is HttpException ||e is ConnectException ||e is TimeoutException ||e is SocketTimeoutException){
                t("网络异常，请稍后重试！")
            }else{
                t("访问失败，请稍后重试！")
            }
        }
    }

    protected fun t(string: String?){
        Toast.makeText(context,string, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示确定取消弹窗
     *
     */
    fun showSureOrCancelDialog(title:String,content:String,negBtnStr:String,posBtnStr:String,cancel:Boolean,listener: ISureDialogListener?){
        val create = AlertDialog.Builder(context!!)
            .setTitle(title)
            .setMessage(content)
            .setCancelable(cancel)
            .setPositiveButton(posBtnStr, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener?.dialogSure()
                }
            })
            .setNegativeButton(negBtnStr, null)
            .create()
        create.show()
        create.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.color333))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (initEventBus()) EventBus.getDefault().unregister(this)
    }
}