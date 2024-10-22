package com.grv.home.di

import com.google.gson.GsonBuilder
import com.grv.common.api.ApiJsonConverterFactory
import com.grv.common.api.AuthInterceptor
import com.grv.common.di.DynamicBaseUrlInterceptor
import com.grv.common.util.SessionManager
import com.grv.home.api.TopNewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

const val NewsHomeData = "newsHomeData"
const val NewsHomeServiceHttpClient = "newsHomeService"

@InstallIn(SingletonComponent::class)
@Module
object HomeNetworkModule {

    @Provides
    fun apiTopNewsService(
        @Named(NewsHomeData) retrofit: Retrofit
    ): TopNewsApi {
        return retrofit.create(TopNewsApi::class.java)
    }

    @Provides
    @Named(NewsHomeData)
    fun retrofitHomeData(
        @Named(NewsHomeServiceHttpClient) okHttpClient: OkHttpClient,
        sessionManager: SessionManager
    ): Retrofit {
        val gson = GsonBuilder().create()

        val converterFactory = GsonConverterFactory.create(gson)
        val baseUrl = sessionManager.getBaseUrl()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ApiJsonConverterFactory(gson))
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Named(NewsHomeServiceHttpClient)
    fun okHttpClientApp(
        okHttpClient: OkHttpClient,
        authInterceptor: AuthInterceptor,
        sessionManager: SessionManager
    ): OkHttpClient {
        val dynamicBaseUrlInterceptor = DynamicBaseUrlInterceptor(sessionManager)

        return okHttpClient
            .newBuilder()
            .addInterceptor(dynamicBaseUrlInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }
}
