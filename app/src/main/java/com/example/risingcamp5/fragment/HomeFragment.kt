package com.example.risingcamp5.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.example.risingcamp5.*
import com.example.risingcamp5.adapters.HomeSimpleAdapter
import com.example.risingcamp5.adapters.HomeCategoryMovieAdapter
import com.example.risingcamp5.adapters.HomeCategoryTitleAdapter
import com.example.risingcamp5.adapters.HomeTopEventAdapter
import com.example.risingcamp5.databinding.FragmentHomeBinding
import com.example.risingcamp5.databinding.ItemHomeCategoryTitleBinding
import com.example.risingcamp5.datas.BoxOfficeMovieRvItem
import com.example.risingcamp5.datas.SimpleItem
import com.example.risingcamp5.models.BoxOfficeItem
import com.example.risingcamp5.models.NaverMovieResponse
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

lateinit var boxOfficeMovieAdapter: HomeCategoryMovieAdapter // 카테고리 영화 내용 어댑터

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    lateinit var topEventAdapter: HomeTopEventAdapter // 탑 이벤트 어댑터
    lateinit var categoryMovieTitleAdapter: HomeCategoryTitleAdapter // 카테고리 영화 타이틀 어댑터
    lateinit var categoryEventTitleAdapter: HomeCategoryTitleAdapter // 카테고리 이벤트 타이틀 어댑터
    lateinit var categoryEventAdatper: HomeSimpleAdapter // 카테고리 이벤트 내용 어댑터
    lateinit var specialOfficialAdapter: HomeSimpleAdapter // 특별관 어댑터

    lateinit var yesterdayDate: String // 어제 날짜


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // 어제 날짜 문자열
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        yesterdayDate = SimpleDateFormat("yyyyMMdd").format(calendar.time)
        Log.d("mainActivity", yesterdayDate)


        // 탑 이벤트 리사이클러뷰
        topEventAdapter = HomeTopEventAdapter()
        topEventAdapter.getListFromView(setTopEventList())
        binding.rvTopEvent.adapter = topEventAdapter

        // 카테고리 타이틀 리사이클러뷰
        categoryMovieTitleAdapter = HomeCategoryTitleAdapter(requireContext())
        categoryMovieTitleAdapter.getListFromView(setCategoryTitleMovieList())
        binding.rvCategoryMovieTitle.adapter = categoryMovieTitleAdapter

        // 박스오피스 카테고리 영화 리사이클러뷰
        boxOfficeMovieAdapter = HomeCategoryMovieAdapter(mutableListOf(), requireContext())
        binding.rvCategoryMovie.adapter = boxOfficeMovieAdapter

        // 이벤트 카테고리 타이틀 리사이클러뷰
        categoryEventTitleAdapter = HomeCategoryTitleAdapter(requireContext())
        categoryEventTitleAdapter.getListFromView(setCategoryEventTitle())
        binding.rvCategoryEventTitle.adapter = categoryEventTitleAdapter

        // 이벤트 카테고리 내용 리사이클러뷰
        categoryEventAdatper = HomeSimpleAdapter()
        categoryEventAdatper.getListFromView(setCategoryEvent(0))
        binding.rvCategoryEvent.adapter = categoryEventAdatper

        // 특별관 리사이클러뷰
        specialOfficialAdapter = HomeSimpleAdapter()
        specialOfficialAdapter.getListFromView(setSpecialOfficial())
        binding.rvSpecialOfficial.adapter = specialOfficialAdapter

        // 메뉴 클릭 리스너
        binding.ivMenu.setOnClickListener {
            startActivity(Intent(requireContext(), MenuActivity::class.java))
        }

        getBoxOfficeMovies() // 박스오피스 영화 데이터 get

        return binding.root
    }

    // API
    // 영화진흥위원회 API(일별 박스오피스) -> 박스오피스 영화 데이터 가져오기
    private fun getBoxOfficeMovies() {
        var retrofit = RetrofitMovieClinet
        retrofit.getBoxOfficeMovieData("boxoffice/searchDailyBoxOfficeList", yesterdayDate, ::onSuccessBoxOfficeMovie)
        // url, 타겟 날짜, 네트워크 호출 성공 함수

    }

    // 박스오피스 영화 데이터를 위한 네트워크 호출하였을 때 성공 시 호출하는 함수
    private fun onSuccessBoxOfficeMovie(movies: List<BoxOfficeItem>) {

        var boxOfficeList = mutableListOf<BoxOfficeMovieRvItem>()
        for(i in 0 until movies.size){

            // 박스오피스 리사이클러뷰 아이템 리스트 셋팅 -> 영화 제목, 순위, 예매율(매출율로 대체)
            var item = BoxOfficeMovieRvItem(movies[i].movieNm, null, movies[i].rank, movies[i].salesShare, null)
            boxOfficeList.add(item)

            // 네이버 영화 검색 API -> 영화 포스터, 관객 평점 데이터 가져옴 (kofic 에서는 비제공)
            getNaverMoviePoster(movies[i].movieNm, i)

            // 영화진흥위원회 API(영화 상세정보) -> 관람등급 명칭 데이터 가져옴
            RetrofitMovieClinet.getMovieInfoData("movie/searchMovieInfo", movies[i].movieCd, i)

        }
        boxOfficeMovieAdapter.appendMovies(boxOfficeList)

    }

    // 네이버 검색 api (박스오피스 영화의 제목으로 검색)
    fun getNaverMoviePoster(title: String, pos: Int) {

        val movieTitle = URLEncoder.encode(title, "UTF-8") // URF-8 인코딩

        val url = URL("https://openapi.naver.com/v1/search/movie.json?query=${movieTitle}&display=1")

        val request = Request.Builder()
            .method("GET", null) // GET 방식으로 요청
            .url(url)
            .addHeader("X-Naver-Client-Id", BuildConfig.NAVER_API_CLIENT_ID)
            .addHeader("X-Naver-Client-Secret", BuildConfig.NAVER_API_CLIENT_SECRET)
            .build()

        // OkHttp 클라이언트 객체 생성, CallBack
        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val movie = gson.fromJson(body, NaverMovieResponse::class.java)

                    // 박스오피스 리사이클러뷰의 각 아이템의 "포스터 이미지"와 "관객 평점" 업데이트
                    var boxOfficeList = boxOfficeMovieAdapter.getListFromAdapter()
                    boxOfficeList[pos].poster = movie.items[0].image
                    boxOfficeList[pos].userRating = movie.items[0].userRating

                    if(pos == 9){
                        Handler(Looper.getMainLooper()).post {
                            boxOfficeMovieAdapter.notifyDataSetChanged() // 데이터 변경 알림
                        }
                    }

                } else {
                    Log.d("movieBookFragment","getMoviePoster-onResponse: Error code ${response.code}"
                    )
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("movieBookFragment", e.message.toString())
            }
        })
    }


    // 리사이클러뷰 아이템 리스트 setting
    fun setTopEventList(): MutableList<Int>{
        var topEventList = mutableListOf<Int>()
        topEventList.add(R.drawable.ic_top_event1)
        topEventList.add(R.drawable.ic_top_event2)
        topEventList.add(R.drawable.ic_top_event3)
        topEventList.add(R.drawable.ic_top_event4)
        topEventList.add(R.drawable.ic_top_event5)
        topEventList.add(R.drawable.ic_top_event6)
        return topEventList
    }

    fun setCategoryTitleMovieList(): MutableList<String>{
        var list = mutableListOf<String>()
        list.add("#박스오피스")
        list.add("#상영예정")
        list.add("#돌비스네마")
        list.add("#라스트특가")
        list.add("#단독")
        list.add("#클소")
        list.add("#필소")
        return list
    }

    fun setCategoryEventTitle(): MutableList<String>{
        var list = mutableListOf<String>()

        list.add("메가Pick")
        list.add("영화")
        list.add("극장")
        list.add("제휴/할인")
        list.add("시사회/무대인사")
        return list
    }

    fun setCategoryEvent(pos: Int): MutableList<SimpleItem>{
        var list = mutableListOf<SimpleItem>()

        if(pos == 0){
            list.add(SimpleItem(R.drawable.ic_event1_1, "2022.09.05-2022.09.30", 1))
            list.add(SimpleItem(R.drawable.ic_event1_2, "2022.09.02-2022.09.13",1))
            list.add(SimpleItem(R.drawable.ic_event1_3, "2022.09.01-2022.09.30",1))
        }else if(pos == 1){
            list.add(SimpleItem(R.drawable.ic_event2_1, "2022.09.21-2022.12.06", 1))
            list.add(SimpleItem(R.drawable.ic_event2_2, "2022.09.17-2022.09.17", 1))
        }
        return list
    }

    fun setSpecialOfficial(): MutableList<SimpleItem>{
        var list= mutableListOf<SimpleItem>()

        list.add(SimpleItem(R.drawable.ic_special_official_1, "DOLBY CINEMA", 2))
        list.add(SimpleItem(R.drawable.ic_special_official_2, "THE BOUTIQUE", 2))
        list.add(SimpleItem(R.drawable.ic_special_official_3, "MX", 2))
        list.add(SimpleItem(R.drawable.ic_special_official_4, "COMFORT", 2))
        list.add(SimpleItem(R.drawable.ic_special_official_5, "PUPPY CINEMA",2))
        list.add(SimpleItem(R.drawable.ic_special_official_6, "MEGABOX KIDS", 2))

        return list
    }
}