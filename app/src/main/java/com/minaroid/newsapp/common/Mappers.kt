package com.minaroid.newsapp.common

import com.minaroid.newsapp.data.model.ArticleDTO
import com.minaroid.newsapp.domain.model.Article

fun List<ArticleDTO>.toDomain(): List<Article> {
    return map {
        Article(
            content = it.content ?: "",
            description = it.description ?: "",
            title = it.title ?: "",
            urlToImage = it.urlToImage ?: ""
        )
    }
}