package com.example.risingcamp5.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.risingcamp5.MovieBookActivity
import com.example.risingcamp5.databinding.ItemHomeEventBinding
import com.example.risingcamp5.databinding.ItemHomeSpecialOfficialBinding
import com.example.risingcamp5.databinding.ItemMenuBinding
import com.example.risingcamp5.datas.SimpleItem

class HomeSimpleAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var list: MutableList<SimpleItem>

    inner class ViewHolder_event(val binding: ItemHomeEventBinding): RecyclerView.ViewHolder(binding.root){
        fun bind_event(item: SimpleItem){
            binding.ivEvent.setImageResource(item.image)
            binding.tvDuration.text = item.text
        }
    }
    inner class ViewHolder_specialOfficial(val binding: ItemHomeSpecialOfficialBinding): RecyclerView.ViewHolder(binding.root){
        fun bind_specialOfficial(item: SimpleItem){
            binding.ivEvent.setImageResource(item.image)
            binding.tvOfficial.text = item.text
        }
    }

    inner class ViewHolder_menu(val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root){
        fun bind_menu(item: SimpleItem){
            binding.ivMenu.setImageResource(item.image)
            binding.tvMenu.text = item.text
        }

        init{
            // "영화별 예매" 메뉴 클릭 리스너
            binding.layoutMenu.setOnClickListener{
                var intent = Intent(binding.layoutMenu.context, MovieBookActivity::class.java)
                ContextCompat.startActivity(binding.layoutMenu.context, intent, null)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> ViewHolder_event(ItemHomeEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            2 -> ViewHolder_specialOfficial(ItemHomeSpecialOfficialBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            3 -> ViewHolder_menu(ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw RuntimeException("HomeSimpleAdapter onCreateViewHolder ERROR")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder_event) holder.bind_event(list[position])
        else if(holder is ViewHolder_specialOfficial) holder.bind_specialOfficial(list[position])
        else if(holder is ViewHolder_menu) holder.bind_menu(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int):Int {
        if(list[position].viewType == 1)
            return 1
        else if(list[position].viewType == 2)
            return 2
        else return 3
    }

    fun getListFromView(nList: MutableList<SimpleItem>){
        list = nList
    }

}