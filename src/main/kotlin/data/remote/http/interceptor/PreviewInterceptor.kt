package data.remote.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PreviewInterceptor(
    val onPreview: (Request) -> Unit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        onPreview(request) // Send it to your UI or logger
        return chain.proceed(request)
    }
}