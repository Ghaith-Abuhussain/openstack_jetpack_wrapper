package data.remote.http.requests.nova.items

import com.google.gson.annotations.SerializedName

data class ServerJsonItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("flavorRef")
    val flavorRef: String? = null,

    @SerializedName("block_device_mapping_v2")
    val blockDeviceMappingV2: List<BlockDeviceMappingJsonItem>? = null,

    @SerializedName("networks")
    val networks: List<NetworkToAddJsonItem>? = null,
)