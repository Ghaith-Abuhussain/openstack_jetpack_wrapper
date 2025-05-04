package data.remote.http.requests.cinder.items

import com.google.gson.annotations.SerializedName

data class NewVolumeJsonItem(
    @SerializedName("size")
    val size: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("availability_zone")
    val availabilityZone: String? = "nova",

    @SerializedName("imageRef")
    val imageRef: String? = null,
)
