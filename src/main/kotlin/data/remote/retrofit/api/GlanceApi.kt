package data.remote.retrofit.api

import data.remote.http.responses.glance.ListOfImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GlanceApi {
    @GET("v2/images")
    suspend fun getImages(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
    ): Response<ListOfImagesResponse>
}