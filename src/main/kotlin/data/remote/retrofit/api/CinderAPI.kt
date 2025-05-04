package data.remote.retrofit.api

import data.remote.http.requests.cinder.CreateVolumeRequest
import data.remote.http.requests.cinder.items.ExistingVolumeJsonItem
import data.remote.http.requests.cinder.items.NewVolumeJsonItem
import data.remote.http.responses.cinder.CreateVolumeResponse
import data.remote.http.responses.cinder.ListOfVolumesResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CinderAPI {

    @GET("v3/{project_id}/volumes/detail")
    suspend fun getListOfVolumes(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
    ): Response<ListOfVolumesResponse>

    @DELETE("v3/{project_id}/volumes/{volume_id}")
    suspend fun deleteVolume(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
        @Path("volume_id") volumeId: String,
    ): Response<ResponseBody>

    @POST("v3/{project_id}/volumes")
    suspend fun createVolumeFromExistingOne(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
        @Body createVolume: CreateVolumeRequest<ExistingVolumeJsonItem>
    ): Response<CreateVolumeResponse>

    @POST("v3/{project_id}/volumes")
    suspend fun createVolume(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("project_id") projectId: String,
        @Body createVolume: CreateVolumeRequest<NewVolumeJsonItem>
    ): Response<CreateVolumeResponse>
}