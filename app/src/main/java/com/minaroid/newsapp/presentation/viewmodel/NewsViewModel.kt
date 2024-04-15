package com.minaroid.newsapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.minaroid.newsapp.common.BaseViewModel
import com.minaroid.newsapp.common.utils.Resource
import com.minaroid.newsapp.domain.usecase.NewsUseCase
import com.minaroid.newsapp.presentation.HomeStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : BaseViewModel() {

//    val articles = mutableStateOf(HomeStateHolder())

    private val _articles: MutableStateFlow<HomeStateHolder> = MutableStateFlow(HomeStateHolder(isLoading = true))
    val articles: StateFlow<HomeStateHolder> = _articles

    init {
        fetchArticles()
    }

    fun fetchArticles(query: String = ""){
        val q = query.ifBlank { "bitcoin" } // TODO default search input
        newsUseCase(q).onEach {
            when (it) {
                is Resource.Loading -> {
                    _articles.value = HomeStateHolder(isLoading = true)
                }

                is Resource.Error -> {
                    _articles.value = HomeStateHolder(error = it.message.toString())
                }

                is Resource.Success -> {
                    _articles.value = HomeStateHolder(data = it.data)
                }
            }
        }.launchIn(viewModelScope)

  }
}