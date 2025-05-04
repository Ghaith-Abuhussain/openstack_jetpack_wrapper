package data.remote.http.responses.nova

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.nova.items.FlavorResponseJsonItem

data class CreateFlavorResponse(
    @SerializedName("flavor")
    val flavor: FlavorResponseJsonItem? = null
)
