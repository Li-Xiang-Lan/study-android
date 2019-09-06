package com.demo.study.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.bean.CourseBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.util.Util
import kotlinx.android.synthetic.main.course_item_layout.view.*

/**
 * Created by cheng on 2019/8/30.
 */
class CourseAdapter(private val context: Context,
                    private val type:String?,
                    private val list: ArrayList<CourseBean>,
                    private val listener:IClickItemListener<CourseBean>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyView(LayoutInflater.from(context).inflate(R.layout.course_item_layout,parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = list[position]
        with(holder.itemView){
            title_text.text=bean.title
            type_text.text=type
            course_num_text.text="${bean.courseCount}课时"
            study_time_text.text="${bean.studyTimes}看过"
            new_price_text.text= Util.fenToYuan(bean.price)+"币"
            old_price_text.paint.isAntiAlias=true
            old_price_text.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG
            old_price_text.text= Util.fenToYuan(bean.originalPrice)+"币"
            Glide.with(context).load(bean.coverUrl).apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(Util.dp2px(context, 6f))
                ).error(R.drawable.logo)
            ).into(cover_img)
        }
    }

    inner class MyView(view: View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener { listener.clickItem(list[layoutPosition]) }
        }
    }
}