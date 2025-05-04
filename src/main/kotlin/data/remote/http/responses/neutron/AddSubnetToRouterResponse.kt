package data.remote.http.responses.neutron

import com.google.gson.annotations.SerializedName

data class AddSubnetToRouterResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("tenant_id")
    val tenantId: String,

    @SerializedName("port_id")
    val portId: String,

    @SerializedName("network_id")
    val networkId: String,

    @SerializedName("subnet_id")
    val subnetId: String,

    @SerializedName("subnet_ids")
    val subnetIds: List<String>
)
