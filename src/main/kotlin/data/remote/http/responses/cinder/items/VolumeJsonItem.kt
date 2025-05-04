package data.remote.http.responses.cinder.items

import com.google.gson.annotations.SerializedName

data class VolumeJsonItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("size")
    val size: Int? = null,

    @SerializedName("availability_zone")
    val availabilityZone: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("name")
    val name: String? = null,
)