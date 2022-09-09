package com.example.risingcamp5.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingcamp5.R
import com.example.risingcamp5.databinding.ItemBookMovieBinding
import com.example.risingcamp5.datas.BookMovieItem
import com.example.risingcamp5.models.BoxOfficeItem

class MovieBookAdapter(var movieList: MutableList<BookMovieItem>, val context: Context): RecyclerView.Adapter<MovieBookAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBookMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: BookMovieItem){
            Log.d("MovieBookAdapter", item.title)
            binding.tvMovie.text = item.title
            Glide.with(binding.ivMovie)
                .load(item.poster)
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivMovie)

            when(item.watchGrade.toString()){
                "All" -> binding.tvWatchGrade.background = getDrawable(context, R.drawable.shape_circle_all)
                "12" -> binding.tvWatchGrade.background =  getDrawable(context, R.drawable.shape_circle_12)
                "15" -> binding.tvWatchGrade.background =  getDrawable(context, R.drawable.shape_circle_15)
            }
            binding.tvWatchGrade.text = item.watchGrade

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun appendMovies(movies: MutableList<BookMovieItem>){
        this.movieList.addAll(movies)
        notifyItemRangeInserted(this.movieList.size, movies.size -1)
    }

}