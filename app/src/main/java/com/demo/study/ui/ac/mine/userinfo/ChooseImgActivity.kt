package com.demo.study.ui.ac.mine.userinfo

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import com.demo.study.R
import com.demo.study.adapter.ChooseImgAdapter
import com.demo.study.base.BaseActivity
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.util.JsonUtil
import kotlinx.android.synthetic.main.recyclerview_content_layout.*
import org.json.JSONArray
import java.util.*

/**
 * Created by cheng on 2019/8/15.
 */
class ChooseImgActivity: BaseActivity() {
    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun titleString(): String = "图片选择"

    override fun setContentId(): Int = R.layout.recyclerview_content_layout

    override fun initView() {
        super.initView()
        setAdapter()
    }

    private fun setAdapter() {
        val single = intent.getBooleanExtra("single", true)
        recyclerView.apply {
            layoutManager= GridLayoutManager(this@ChooseImgActivity,3)
            adapter= ChooseImgAdapter(this@ChooseImgActivity,getImgList(),single,object:
                IClickItemListener<ArrayList<String>> {
                override fun clickItem(t: ArrayList<String>) {
                    val intent = Intent()
                    if (single){
                        intent.putExtra("url",t[0])
                    }
                    setResult(0,intent)
                    finish()
                }
            })
        }
    }

    private fun getImgList():ArrayList<String>{
        val list= arrayListOf<String>()
        val json = JsonUtil.getJsonString(this, "img.json")
        val jsonArray = JSONArray(json)
        for (index in 0 until jsonArray.length()){
            val string = jsonArray.getString(index)
            list.add(string)
        }
        Collections.shuffle(list)
        return list;
    }
}