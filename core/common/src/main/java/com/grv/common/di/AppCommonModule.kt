package com.grv.common.di

import android.content.Context
import com.grv.common.util.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppCommonModule {

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}

class DynamicBaseUrlInterceptor(private val sessionManager: SessionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val newBaseUrl = sessionManager.getBaseUrl()?.toHttpUrl()
        val newUrl = newBaseUrl?.let {
            request.url.newBuilder()
                .scheme(it.scheme)
                .host(newBaseUrl.host)
                .port(newBaseUrl.port)
                .build()
        }

        if (newUrl != null) request = request.newBuilder().url(newUrl).build()

        return chain.proceed(request)
    }
}