package com.iedrania.githopper.database.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", authToken)
        return chain.proceed(requestBuilder.build())
    }
}
