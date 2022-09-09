package com.example.risingcamp5.adapters

import android.content.Context
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.risingcamp5.R
import com.example.risingcamp5.databinding.ItemHomeCategoryTitleBinding

class HomeCategoryTitleAdapter(var context: Context): RecyclerView.Adapter<HomeCategoryTitleAdapter.ViewHolder>() {

    lateinit var titleList: MutableList<String>
    var itemList = mutableListOf<ItemHomeCategoryTitleBinding>()

    inner class ViewHolder(val binding: ItemHomeCategoryTitleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(title: String){

            if(title == "#박스오피스" || title == "메가Pick"){
                binding.tvCategoryTitle.setTextColor(ContextCompat.getColor(context, R.color.app_bg))
                binding.underLine.visibility = View.VISIBLE
            }
            binding.tvCategoryTitle.text = title

        }
        init{
            itemList.add(binding)
            binding.layoutTitle.setOnClickListener { // 카테고리 타이틀 클릭리스너
                for(i in 0 until itemList.size){
                    itemList[i].tvCategoryTitle.setTextColor(context.getColor(R.color.category_txt))
                    itemList[i].underLine.visibility = View.INVISIBLE
                }

                binding.tvCategoryTitle.setTextColor(context.getColor(R.color.app_bg))
                binding.underLine.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): HomeCategoryTitleAdapter.ViewHolder {
        return ViewHolder(ItemHomeCategoryTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeCategoryTitleAdapter.ViewHolder, position: Int) {
        holder.bind(titleList[position])
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    fun getListFromView(nList: MutableList<String>){
        titleList = nList
    }

}