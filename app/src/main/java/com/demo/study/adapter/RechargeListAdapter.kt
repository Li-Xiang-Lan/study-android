package com.demo.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.study.R
import com.demo.study.bean.RechargeListBean
import com.demo.study.util.Util
import kotlinx.android.synthetic.main.recharge_item_layout.view.*

/**
 * Created by cheng on 2019/9/6.
 */
class RechargeListAdapter(private val context: Context, private val list: ArrayList<RechargeListBean>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var choosePos=-1

    fun getChooseBean():RechargeListBean?=if (choosePos==-1) null else list[choosePos]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyView(LayoutInflater.from(context).inflate(R.layout.recharge_item_layout,parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView){
            val bean = list[position]
            num_text.text= Util.fenToYuan(bean.money)
            money_num_text.text="原价：${Util.fenToYuan(bean.oldMoney)}"
            item_layout.isSelected=choosePos==position
        }
    }

    inner class MyView(view: View):RecyclerView.ViewHolder(view){

        init {
            view.setOnClickListener {
                choosePos=layoutPosition
                notifyDataSetChanged()
            }
        }
    }
}