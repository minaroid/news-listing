package com.minaroid.newsapp.domain.usecase

import com.minaroid.newsapp.common.utils.Resource
import com.minaroid.newsapp.domain.model.Article
import com.minaroid.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(query: String = ""): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading(""))
        try {
            emit(Resource.Success(newsRepository.fetchArticles(query)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}