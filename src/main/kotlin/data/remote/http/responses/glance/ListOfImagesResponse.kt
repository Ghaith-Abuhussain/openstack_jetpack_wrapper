package data.remote.http.responses.glance

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.glance.items.ImageResponseJsonItem

data class ListOfImagesResponse(
    @SerializedName("images")
    val images: List<ImageResponseJsonItem>? = null,
)
