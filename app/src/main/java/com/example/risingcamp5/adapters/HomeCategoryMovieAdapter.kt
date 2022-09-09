package com.example.risingcamp5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingcamp5.R
import com.example.risingcamp5.databinding.ItemHomeBoxofficeMovieBinding
import com.example.risingcamp5.datas.BoxOfficeMovieRvItem

class HomeCategoryMovieAdapter(var movieList : MutableList<BoxOfficeMovieRvItem>, val context: Context): RecyclerView.Adapter<HomeCategoryMovieAdapter.ViewHolder>() {

    private var list: MutableList<BoxOfficeMovieRvItem>

    init{
        this.list = movieList
    }

    inner class ViewHolder(val binding: ItemHomeBoxofficeMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: BoxOfficeMovieRvItem){ // 박스오피스 영화 아이템

            binding.tvMovieTitle.text = item.title

            Glide.with(binding.ivMoviePoster)
                .load(item.poster)
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivMoviePoster)

            binding.tvRank.text = item.rank
            binding.tvBookingRate.text = "예매율 "+item.bookingRate+"% · "
            binding.tvUserRating.text = item.userRating.toString()
            when(item.watchGrade.toString()){
                "All" -> binding.tvWatchGrade.background = getDrawable(context, R.drawable.shape_circle_all)
                "12" -> binding.tvWatchGrade.background = getDrawable(context, R.drawable.shape_circle_12)
                "15" -> binding.tvWatchGrade.background = getDrawable(context, R.drawable.shape_circle_15)
            }
            binding.tvWatchGrade.text = item.watchGrade
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): HomeCategoryMovieAdapter.ViewHolder {
        return ViewHolder(ItemHomeBoxofficeMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeCategoryMovieAdapter.ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun appendMovies(movies: MutableList<BoxOfficeMovieRvItem>){
        this.movieList.addAll(movies)
        notifyItemRangeInserted(this.movieList.size, movies.size -1)
    }

    fun getListFromAdapter(): MutableList<BoxOfficeMovieRvItem>{
        return this.list
    }

}