package com.minaroid.newsapp.domain.repository

import com.minaroid.newsapp.domain.model.Article

interface NewsRepository {

    suspend fun fetchArticles(query: String = ""): List<Article>
}