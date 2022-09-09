package com.example.risingcamp5.models


// 일별 박스오피스 영화
data class BoxOfficeMovieResponse(
    val boxOfficeResult: BoxOfficeResult
)

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<BoxOfficeItem>,
    val showRange: String
)

data class BoxOfficeItem(
    val audiAcc: String,
    val audiChange: String,
    val audiCnt: String,
    val audiInten: String,
    val movieCd: String,
    val movieNm: String,
    val openDt: String,
    val rank: String,
    val rankInten: String,
    val rankOldAndNew: String,
    val rnum: String,
    val salesAcc: String,
    val salesAmt: String,
    val salesChange: String,
    val salesInten: String,
    val salesShare: String,
    val scrnCnt: String,
    val showCnt: String
)