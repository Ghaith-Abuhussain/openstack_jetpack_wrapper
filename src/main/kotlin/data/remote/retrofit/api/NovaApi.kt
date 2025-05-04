package data.remote.retrofit.api

import data.remote.http.requests.nova.BootServerRequest
import data.remote.http.requests.nova.CreateNewFlavorRequest
import data.remote.http.responses.nova.CreateFlavorResponse
import data.remote.http.responses.nova.ListOfFlavorsResponse
import data.remote.http.responses.nova.ListOfInstancesResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NovaApi {

    @GET("v2.1/{project_id}/flavors")
    suspend fun getFlavors(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
    ): Response<ListOfFlavorsResponse>

    @DELETE("v2.1/flavors/{flavor_id}")
    suspend fun deleteFlavor(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("flavor_id") flavorId: String,
    ): Response<ResponseBody>

    @GET("v2.1/{project_id}/servers/detail")
    suspend fun getInstances(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
    ): Response<ListOfInstancesResponse>

    @DELETE("v2.1/{project_id}/servers/{server_id}")
    suspend fun deleteInstance(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
        @Path("server_id") serverId: String,
    ): Response<ResponseBody>

    @POST("v2.1/flavors")
    suspend fun addFlavor(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Body createNewFlavorRequest: CreateNewFlavorRequest
    ): Response<CreateFlavorResponse>

    @POST("v2.1/{project_id}/servers")
    suspend fun bootServer(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
        @Body bootServerRequest: BootServerRequest
    ): Response<ResponseBody>
}