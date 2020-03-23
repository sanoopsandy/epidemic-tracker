package `in`.tracker.core.api

import `in`.tracker.core.BuildConfig.RAPID_API_HOST
import `in`.tracker.core.BuildConfig.RAPID_API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest = chain.request()
        newRequest = newRequest.newBuilder()
            .addHeader("x-rapidapi-host", RAPID_API_HOST)
            .addHeader("x-rapidapi-key", RAPID_API_KEY)
            .build()

        return chain.proceed(newRequest)
    }
}