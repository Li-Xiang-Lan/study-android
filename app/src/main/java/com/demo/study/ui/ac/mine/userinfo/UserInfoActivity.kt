package com.demo.study.ui.ac.mine.userinfo

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.base.MvpBaseActivity
import com.demo.study.bean.EventBusBean
import com.demo.study.util.CodeUtil
import com.demo.study.util.SPUtil
import kotlinx.android.synthetic.main.user_info_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by cheng on 2019/9/4.
 */
class UserInfoActivity:MvpBaseActivity<UserInfoPresenter>(),UserInfoContract.UserInfoView {
    private var headPath=""
    private val sexArray = arrayOf("男", "女")

    override fun initPresenter(): UserInfoPresenter = UserInfoPresenter()

    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun setContentId(): Int = R.layout.user_info_layout

    override fun titleString(): String = "个人资料"

    override fun initView() {
        super.initView()
        setUserInfo()
    }

    override fun saveUserInfoSuccess() {
        val userInfo = SPUtil.getInstance()?.getUserInfo()
        userInfo?.headUrl=headPath
        userInfo?.userName=name_edit.text.toString()
        userInfo?.userSex=if (sex_text.text.toString().equals("女")) 2 else 1
        SPUtil.getInstance()?.saveUserInfo(userInfo)
        EventBus.getDefault().post(EventBusBean(CodeUtil.SAVE_USERINFO_SUCCESS))
        t("保存信息成功")
        finish()
    }

    override fun setListener() {
        super.setListener()
        head_img.setOnClickListener { startActivityForResult(Intent(this,ChooseImgActivity::class.java),0) }
        sex_text.setOnClickListener { chooseSex() }
        save_text.setOnClickListener {
            if (TextUtils.isEmpty(name_edit.text.toString().trim { it <= ' ' })) {
                t("请输入昵称")
            }else{
                presenter?.saveUserInfo(name_edit.text.toString(),headPath,if (sex_text.text.toString().equals("女")) 2 else 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0&&null!=data){
            headPath=data?.getStringExtra("url")
            Glide.with(this).load(headPath)
                .apply(
                    RequestOptions.circleCropTransform()
                        .error(R.drawable.logo))
                .into(head_img)
        }
    }

    private fun chooseSex() {

        var index=sexArray.indexOf(sex_text.text.toString())
        AlertDialog.Builder(this).apply {
            setTitle("请选择性别")
            setSingleChoiceItems(sexArray,index,object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    index=which
                }
            })
            setPositiveButton("确定",object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    sex_text.text=sexArray[index]
                }
            })
            show()
        }
    }

    private fun setUserInfo() {
        val userInfo = SPUtil.getInstance()?.getUserInfo()
        name_edit.setText(userInfo?.userName)
        sex_text.text=if (userInfo?.userSex==1) "男" else "女"
        headPath=userInfo?.headUrl!!
        Glide.with(this).load(userInfo?.headUrl)
            .apply(
                RequestOptions.circleCropTransform()
                    .error(R.drawable.logo))
            .into(head_img)
    }
}