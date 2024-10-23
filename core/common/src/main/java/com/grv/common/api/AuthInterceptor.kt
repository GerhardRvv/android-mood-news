package com.grv.common.api

import com.grv.common.util.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        val token = sessionManager.getAuthToken()
        requestBuilder.header("Accept", "application/json")
        requestBuilder.header("Authorization", "Bearer $token") // Comment to test mocked response

        val originalUrl = originalRequest.url
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("api-key", token) // Comment to test mocked response
            .build()

        requestBuilder.url(urlWithApiKey)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
