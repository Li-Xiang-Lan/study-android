package com.demo.study.ui.ac.mine.login

import android.text.Editable
import android.text.TextWatcher
import com.demo.study.R
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.CodeBean
import com.demo.study.bean.EventBusBean
import com.demo.study.bean.UserInfo
import com.demo.study.util.CodeUtil
import com.demo.study.util.SPUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.login_layout.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * Created by cheng on 2019/9/3.
 */
class LoginActivity:MvpBaseActivity<LoginPresenter>(),LoginContract.LoginView, TextWatcher {

    private var disposable: Disposable?=null

    override fun initPresenter(): LoginPresenter = LoginPresenter()

    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun setContentId(): Int = R.layout.login_layout

    override fun titleString(): String = ""

    override fun getCodeSuccess(codeBean: CodeBean) {
        code_edit.setText(codeBean.code)
    }

    override fun getCodeFail() {
        disposable?.dispose()
        get_code_text.text = "重新获取"
        get_code_text.isClickable = true
    }

    override fun loginSuccess(userInfo: UserInfo) {
        SPUtil.getInstance()?.saveUserInfo(userInfo)
        EventBus.getDefault().post(EventBusBean(CodeUtil.LOGIN_SUCCESS))
        t("登陆成功")
        finish()
    }

    override fun setListener() {
        super.setListener()
        phone_edit.addTextChangedListener(this)
        code_edit.addTextChangedListener(this)
        //获取验证码
        get_code_text.setOnClickListener {
            getCode()
        }
        //登录
        login_text.setOnClickListener {
            if (!agree_check.isChecked) t("请阅读并同意使用协议")
            else presenter?.login(phone_edit.text.toString(),code_edit.text.toString())
        }
        //服务协议
        preview_agree_text.setOnClickListener {
//            val intent = Intent(this, WebActivity::class.java)
//            intent.putExtra("title", "人人享学服务协议")
//            intent.putExtra("url", "https://app.cdxiangxue.com.cn/user/service.html")
//            startActivity(intent)
        }
    }

    private fun getCode() {
        val phone = phone_edit.text.toString()
        if (phone.length != 11 ||!phone.startsWith("1")) {
            t("请输入正确的手机号!")
            return
        }
        //获取验证码
        presenter?.getCode(phone)
        get_code_text.isClickable = false
        get_code_text.isSelected=false
        disposable = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { aLong -> get_code_text.text = (60 - aLong).toString()+ "s" }
            .doOnComplete {
                get_code_text.text = "重新获取"
                get_code_text.isClickable = true
                get_code_text.isSelected = phone_edit.text.toString().length == 11
            }
            .subscribe()
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = phone_edit.text.toString().length == 11
        val code = code_edit.text.toString().length == 6
        login_text.isSelected=phone&&code
        get_code_text.isSelected=phone&&get_code_text.isClickable
    }
}