package com.demo.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.study.R
import com.demo.study.interfaces.IClickItemListener
import kotlinx.android.synthetic.main.mine_item_layout.view.*

/**
 * Created by cheng on 2019/9/4.
 */
class MineAdapter(private val context: Context,private val listener: IClickItemListener<Int>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val icon= intArrayOf(R.drawable.mine_10)
    private val content= arrayOf("钱包")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyView(LayoutInflater.from(context).inflate(R.layout.mine_item_layout,parent,false))

    override fun getItemCount(): Int = icon.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView){
            content_text.text=content[position]
            icon_img.setImageResource(icon[position])
        }
    }

    inner class MyView(view: View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener { listener.clickItem(layoutPosition) }
        }
    }
}