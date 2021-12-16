package com.example.zulipapp.data.api

import okhttp3.Credentials
import okhttp3.Interceptor

class BasicAuthInterceptor(email: String, password: String) : Interceptor {

    private val credentials = Credentials.basic(email, password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}