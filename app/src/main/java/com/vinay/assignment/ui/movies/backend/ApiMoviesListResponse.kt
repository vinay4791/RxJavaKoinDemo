package com.vinay.assignment.ui.movies.backend



data class ApiMoviesListResponse(val results: List<Results>,
                                 val page: Int,
                                 val total_results: Int,
                                 val dates: Dates,
                                 val total_pages: Int)

data class Results(
    val popularity: Double?,
    val vote_count: Int?,
    val video: Boolean?,
    val poster_path: String?,
    val id: Int?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val title: String?,
    val vote_average: Double?,
    val overview: String?,
    val release_date: String?
)

data class Dates(
    val maximum: String?,
    val minimum: String?
)