package com.demo.study.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.study.R
import com.demo.study.interfaces.IClickItemListener
import kotlinx.android.synthetic.main.choose_img_item_layout.view.*


/**
 * Created by cheng on 2019/8/15.
 */
class ChooseImgAdapter(private val context: Context,
                       private val list: ArrayList<String>,
                       private val single: Boolean,
                       private val listener: IClickItemListener<ArrayList<String>>
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chooseList= arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyView(LayoutInflater.from(context).inflate(
        R.layout.choose_img_item_layout,parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView){
            gouxuan_img.visibility=if (single) View.GONE else View.VISIBLE
            gouxuan_img.isSelected=chooseList.contains(list[position])
            Glide.with(context).load(list[position]).apply(RequestOptions.centerCropTransform()).into(img)
        }
    }


    inner class MyView(view: View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                if (single){
                    chooseList.clear()
                    chooseList.add(list[layoutPosition])
                    listener.clickItem(chooseList)
                }else{

                }
            }
        }
    }
}