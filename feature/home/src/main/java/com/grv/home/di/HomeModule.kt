package com.grv.home.di

import com.grv.home.usecase.GetTopNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun provideHomeUseCaseProvider(
        getTopNewsUseCase: GetTopNewsUseCase
    ): HomeUseCaseProvider = HomeUseCaseProviderImpl(
        getTopNewsUseCase
    )
}

interface HomeUseCaseProvider {
    val getTopNewsUseCase: GetTopNewsUseCase
}

class HomeUseCaseProviderImpl @Inject constructor(
    override val getTopNewsUseCase: GetTopNewsUseCase
) : HomeUseCaseProvider
