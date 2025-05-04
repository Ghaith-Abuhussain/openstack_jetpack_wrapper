package data.remote.http.requests.cinder.items

import com.google.gson.annotations.SerializedName

data class ExistingVolumeJsonItem(
    @SerializedName("size")
    val size: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("source_volid")
    val sourceVolid: String? = null,
)
