package com.minaroid.newsapp.presentation

import com.minaroid.newsapp.domain.model.Article

data class HomeStateHolder(
    val isLoading: Boolean = false,
    val data: List<Article>? = null,
    val error: String = ""
)
