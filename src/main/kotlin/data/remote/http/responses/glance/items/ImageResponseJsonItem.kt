package data.remote.http.responses.glance.items

import com.google.gson.annotations.SerializedName

data class ImageResponseJsonItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,
)
