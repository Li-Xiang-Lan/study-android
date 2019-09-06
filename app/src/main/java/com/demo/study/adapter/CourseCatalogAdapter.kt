package com.demo.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.study.R
import com.demo.study.bean.CourseCatalogBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.util.TimeFormater
import kotlinx.android.synthetic.main.course_catalog_item_layout.view.*
import kotlinx.android.synthetic.main.course_item_layout.view.title_text


/**
 * 课程目录适配器
 * Created by cheng on 2019/7/26.
 */
class CourseCatalogAdapter(private val context: Context?,
                           private val list: ArrayList<CourseCatalogBean>,
                           private val listener: IClickItemListener<Int>
):RecyclerView.Adapter<CourseCatalogAdapter.MyView>() {
    private var level=-1
    private var playing=-1

    fun setPlaying(playing:Int){
        this.playing=playing
        notifyDataSetChanged()
    }

    fun setLevel(level: Int){
        this.level=level
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView = MyView(LayoutInflater.from(context).inflate(
        R.layout.course_catalog_item_layout,parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val bean = list[position]
        with(holder.itemView){
            title_text.text=bean.title
            time_text.text= TimeFormater.formatMs(bean.times)
            title_text.isSelected=playing==position
        }
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener { listener.clickItem(layoutPosition) }
        }
    }
}