package com.example.zulipapp.data.api

import okhttp3.Credentials
import okhttp3.Interceptor

class BasicAuthInterceptor : Interceptor {

    private val credentials = Credentials.basic(
        "e.jolk@yahoo.com",
        "6MtxtdXVOwu35cHkoiQ0xoog6rOMlyRm"
    )

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}