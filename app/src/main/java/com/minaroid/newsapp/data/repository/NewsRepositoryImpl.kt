package com.minaroid.newsapp.data.repository

import com.minaroid.newsapp.common.toDomain
import com.minaroid.newsapp.data.model.NewsDTO
import com.minaroid.newsapp.data.remote.NetworkManager
import com.minaroid.newsapp.domain.model.Article
import com.minaroid.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val networkManager: NetworkManager) : NewsRepository {

    override suspend fun fetchArticles(q: String): List<Article> {
        val query: HashMap<String, Any> = HashMap()
        query["apiKey"] = "" // TODO move to config..
        query["q"] = q
//        query["pageSize"] = 1
        val news  = this.networkManager.getRequest(api = "/v2/everything", query = query, parseClass = NewsDTO::class.java)
        return news.articles?.toDomain()!!
    }
}