package com.example.risingcamp5.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingcamp5.databinding.ItemHomeTopEventBinding

class HomeTopEventAdapter: RecyclerView.Adapter<HomeTopEventAdapter.ViewHolder>() {
    lateinit var list : MutableList<Int>

    inner class ViewHolder(val binding: ItemHomeTopEventBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){
            binding.ivEvent.setImageResource(image)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopEventAdapter.ViewHolder {
        return ViewHolder(ItemHomeTopEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeTopEventAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<Int>){
        list = nList
    }
}