package data.remote.http.requests.nova.items

import com.google.gson.annotations.SerializedName

data class NewFlavorJsonItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("ram")
    val ram: Int? = null,

    @SerializedName("vcpus")
    val vcpus: Int? = 1,

    @SerializedName("disk")
    val disk: Int? = 0,

    @SerializedName("id")
    val id: String? = "auto",

    @SerializedName("swap")
    val swap: Int? = 0,

    @SerializedName("rxtx_factor")
    val rxtxFactor: Double? = 1.0,

    @SerializedName("os-flavor-access:is_public")
    val osFlavorAccessIsPublic: Boolean? = true
)