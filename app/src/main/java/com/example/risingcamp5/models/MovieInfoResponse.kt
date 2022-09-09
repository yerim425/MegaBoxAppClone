package com.example.risingcamp5.models

// 영화 상세정보
data class MovieInfoResponse(
    val movieInfoResult: MovieInfoResult
)

data class MovieInfoResult(
    val movieInfo: MovieInfo,
    val source: String
)

data class MovieInfo(
    val audits: List<Audit>,
)

data class Audit(
    val watchGradeNm: String
)