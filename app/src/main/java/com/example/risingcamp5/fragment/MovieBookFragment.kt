package com.example.risingcamp5.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.risingcamp5.adapters.MovieBookAdapter
import com.example.risingcamp5.databinding.FragmentMovieBookBinding
import com.example.risingcamp5.datas.BookMovieItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter


var movieBookAdapter: MovieBookAdapter ?= null

class MovieBookFragment : Fragment() {
    lateinit var binding: FragmentMovieBookBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBookBinding.inflate(layoutInflater)


        // 영화 선택 리사이클러뷰
        binding.rvMovieChoice.layoutManager = GridLayoutManager(context, 3)
        movieBookAdapter = MovieBookAdapter(mutableListOf(), requireContext())
        binding.rvMovieChoice.adapter = movieBookAdapter

        var bookMovieList = mutableListOf<BookMovieItem>()
        var boxOfficeList = boxOfficeMovieAdapter.getListFromAdapter()
        for(i in 0 until boxOfficeList.size){
            bookMovieList.add(BookMovieItem(boxOfficeList[i].title, boxOfficeList[i].poster, boxOfficeList[i].watchGrade))
        }
        movieBookAdapter!!.appendMovies(bookMovieList)


        return binding.root
    }
}







