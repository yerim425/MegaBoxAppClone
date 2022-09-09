package com.example.risingcamp5

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.risingcamp5.fragment.boxOfficeMovieAdapter
import com.example.risingcamp5.models.BoxOfficeMovieResponse
import com.example.risingcamp5.models.BoxOfficeItem
import com.example.risingcamp5.models.MovieInfoResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMovieClinet {

    //영화진흥위원회 API URL
    private const val MOVIE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/"


    var movieRetrofit = initRetrofit()
    fun initRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    // 일별 박스오피스 영화 데이터 가져옴
    fun getBoxOfficeMovieData(type: String, targetDt: String, OnSuccess: (movies: List<BoxOfficeItem>) -> Unit){
        val movieInterface = movieRetrofit.create(MovieInterface::class.java)
        movieInterface.getBoxOfficeMovies(type, targetDt).enqueue(object: Callback<BoxOfficeMovieResponse>{
            @SuppressLint("setTextI18n")
            override fun onResponse(
                call: Call<BoxOfficeMovieResponse>,
                response: Response<BoxOfficeMovieResponse>
            ) {
                if(response.isSuccessful){
                    var result = response.body() as BoxOfficeMovieResponse
                    if(result != null){
                        OnSuccess.invoke(result.boxOfficeResult.dailyBoxOfficeList)
                    }else{
                        Log.d("RetrofitMovieClient", "reponse.body() is null")
                    }
                }else{
                    Log.d("RetrofitMovieClient", "getBoxOfficeMovieData-onResponse: Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<BoxOfficeMovieResponse>, t: Throwable) {
                Log.d("RetrofitMovieClient", t.message ?: "통신오류")
            }

        })
    }

    // 영화 상세정보 데이터 가져옴
    fun getMovieInfoData(type: String, code: String, pos: Int){

        val movieInterface = movieRetrofit.create(MovieInterface::class.java)
        movieInterface.getMovieInfo(type, code).enqueue(object: Callback<MovieInfoResponse>{
            override fun onResponse(
                call: Call<MovieInfoResponse>,
                response: Response<MovieInfoResponse>
            ) {
                if(response.isSuccessful){
                    var result = response.body() as MovieInfoResponse
                    if(result != null){

                        // 영화 관람 등급
                        var watchGrade: String ?= null
                        when(result.movieInfoResult.movieInfo.audits[0].watchGradeNm){
                            "전체관람가" -> watchGrade = "All"
                            "12세이상관람가" -> watchGrade = "12"
                            "15세이상관람가" -> watchGrade = "15"
                        }
                        boxOfficeMovieAdapter.movieList[pos].watchGrade = watchGrade

                        if(pos == 9){
                            Handler(Looper.getMainLooper()).post {
                                boxOfficeMovieAdapter.notifyDataSetChanged() // 데이터 변경 알림
                            }
                        }
                    }else{
                        Log.d("RetrofitMovieClient", "reponse.body() is null")
                    }
                }else{
                    Log.d("RetrofitMovieClient", "getMovieInfoData-onResponse: Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<MovieInfoResponse>, t: Throwable) {
                Log.d("RetrofitMovieClient", t.message ?: "통신오류")
            }
        })
    }
}
