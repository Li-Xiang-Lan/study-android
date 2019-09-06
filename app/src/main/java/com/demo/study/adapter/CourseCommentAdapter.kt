package com.demo.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.bean.CourseCommentBean
import com.demo.study.util.TimeFormater
import kotlinx.android.synthetic.main.course_comment_item_layout.view.*

/**
 * 课程评论 适配器
 * Created by cheng on 2019/7/29.
 */
class CourseCommentAdapter(private val context: Context?,private val list: ArrayList<CourseCommentBean>):RecyclerView.Adapter<CourseCommentAdapter.MyView>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView = MyView(LayoutInflater.from(context).inflate(
        R.layout.course_comment_item_layout,parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder:MyView, position: Int) {
        val bean = list[position]
        with(holder.itemView){
            name_text.text=bean.user.userName
            time_text.text=TimeFormater.formatTime(bean.createTime)
            content_text.text=bean.content
            Glide.with(context).load(bean.user.headUrl).apply(RequestOptions.circleCropTransform().error(R.drawable.logo)).into(head_img)
        }
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view)
}