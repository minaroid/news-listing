package com.minaroid.newsapp.domain.di

import com.minaroid.newsapp.data.remote.NetworkManager
import com.minaroid.newsapp.data.repository.NewsRepositoryImpl
import com.minaroid.newsapp.domain.repository.NewsRepository
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
 object DomainModule {

    @Provides
    fun provideNewsRepository(networkManager: NetworkManager): NewsRepository {
        return NewsRepositoryImpl(networkManager)
    }
}
