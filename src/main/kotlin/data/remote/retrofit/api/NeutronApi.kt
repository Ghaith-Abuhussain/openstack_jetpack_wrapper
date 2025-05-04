package data.remote.retrofit.api

import data.remote.http.requests.neutron.AddSubnetToRouterRequest
import data.remote.http.requests.neutron.CreateNetworkRequest
import data.remote.http.requests.neutron.CreateRouterRequest
import data.remote.http.requests.neutron.CreateSubnetRequest
import data.remote.http.responses.neutron.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NeutronApi {

    @GET("v2.0/networks")
    suspend fun getNetworks(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
    ): Response<ListOfNetworksResponse>

    @DELETE("v2.0/networks/{network_id}")
    suspend fun deleteNetwork(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("network_id") networkId: String,
    ): Response<ResponseBody>

    @GET("v2.0/routers")
    suspend fun getRouters(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
    ): Response<ListOfRoutersResponse>

    @DELETE("v2.0/routers/{router_id}")
    suspend fun deleteRouter(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("router_id") routerId: String,
    ): Response<ResponseBody>

    @PUT("v2.0/routers/{router_id}/add_router_interface")
    suspend fun addSubNetToRouter(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Path("router_id") routerId: String,
        @Body body: AddSubnetToRouterRequest
    ): Response<AddSubnetToRouterResponse>

    @POST("v2.0/routers")
    @Headers("Content-Type: application/json")
    suspend fun createNewRouter(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Body body: CreateRouterRequest
    ): Response<CreateRouterResponse>

    @POST("v2.0/networks")
    @Headers("Content-Type: application/json")
    suspend fun createNetwork(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Body body: CreateNetworkRequest
    ): Response<CreateNetworkResponse>

    @POST("v2.0/subnets")
    @Headers("Content-Type: application/json")
    suspend fun createSubnet(
        @Header("X-Auth-Token") xAuthToken: String,
        @Header("X-Subject-Token") xSubjectToken: String,
        @Body body: CreateSubnetRequest
    ): Response<CreateSubnetResponse>
}