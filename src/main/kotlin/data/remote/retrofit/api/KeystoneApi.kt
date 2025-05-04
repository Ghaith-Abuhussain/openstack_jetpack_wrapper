package data.remote.retrofit.api

import data.remote.http.requests.keystone.KeystoneAuthenticationRequest
import data.remote.http.responses.keystone.AuthenticationJsonResponse
import data.remote.http.responses.keystone.ListOfProjectsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface KeystoneApi {
    @GET("v3/projects")
    suspend fun getProjects(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String
    ): Response<ListOfProjectsResponse>

    @POST("v3/auth/tokens")
    suspend fun authenticate(@Body request: KeystoneAuthenticationRequest): Response<AuthenticationJsonResponse>
}