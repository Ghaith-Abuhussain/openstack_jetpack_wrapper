package data.remote.http.requests.nova.items

import com.google.gson.annotations.SerializedName

data class BlockDeviceMappingJsonItem(
    @SerializedName("boot_index")
    val bootIndex: Int? = 0,

    @SerializedName("uuid")
    val uuid: String? = null,

    @SerializedName("source_type")
    val sourceType: String? = "volume",

    @SerializedName("destination_type")
    val destinationType: String? = "volume",

    @SerializedName("delete_on_termination")
    val deleteOnTermination: Boolean? = null,
)