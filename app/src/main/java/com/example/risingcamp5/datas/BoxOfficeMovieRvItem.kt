package com.example.risingcamp5.datas

data class BoxOfficeMovieRvItem(
    var title: String,
    var poster: String ?= null,
    var rank: String,
    var bookingRate: String,
    var userRating: String ?= null,
    var watchGrade: String ?= null
)
