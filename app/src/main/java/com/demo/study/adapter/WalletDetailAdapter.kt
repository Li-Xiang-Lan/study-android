package com.demo.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.study.R
import com.demo.study.bean.WalletDetailBean
import com.demo.study.interfaces.IClickItemListener
import com.demo.study.util.TimeFormater
import com.demo.study.util.Util
import kotlinx.android.synthetic.main.wallet_detail_item_layout.view.*

/**
 * Created by cheng on 2019/9/6.
 */
class WalletDetailAdapter(private val context: Context,
                          private val list: ArrayList<WalletDetailBean>,
                          private val listener: IClickItemListener<WalletDetailBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyView(LayoutInflater.from(context).inflate(R.layout.wallet_detail_item_layout,parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView){
            val bean = list[position]
            name_text.text=if (bean.type==1) "购买课程：${bean.title}" else "充值"
            amount_text.text=if (bean.type==1) "-${Util.fenToYuan(bean.price)}" else "+${Util.fenToYuan(bean.price)}"
            time_text.text=TimeFormater.formatTime(bean.createTime)
        }
    }

    inner class MyView(view: View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener { listener.clickItem(list[layoutPosition]) }
        }
    }
}