package com.minaroid.newsapp.data.model

data class NewsDTO(
    val articles: List<ArticleDTO>?,
    val status: String?,
    val totalResults: Int?
)