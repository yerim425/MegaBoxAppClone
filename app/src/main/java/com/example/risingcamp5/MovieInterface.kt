package com.example.risingcamp5

import com.example.risingcamp5.models.BoxOfficeMovieResponse
import com.example.risingcamp5.models.BoxOfficeResult
import com.example.risingcamp5.models.MovieInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    // boxoffice/searchDailyBoxOfficeList.json? -> 박스오피스
    @GET("{type}.json?")
    fun getBoxOfficeMovies(
        @Path("type") type: String,
        @Query("targetDt") targetDt: String,
        @Query("key") key: String = BuildConfig.KOBIS_API_KEY

    ) : Call<BoxOfficeMovieResponse>


    // movie/searchMovieInfo.json? -> 영화 상세정보
    @GET("{type}.json?")
    fun getMovieInfo(
        @Path("type") type: String,
        @Query("movieCd") movidCd: String,
        @Query("key") key: String = BuildConfig.KOBIS_API_KEY
    ) : Call<MovieInfoResponse>

}