package com.example.risingcamp5.models

// 네이버 영화

data class NaverMovieResponse(
    val items: List<NaverItem>
)

data class NaverItem(
    val image: String,
    val userRating: String
)
