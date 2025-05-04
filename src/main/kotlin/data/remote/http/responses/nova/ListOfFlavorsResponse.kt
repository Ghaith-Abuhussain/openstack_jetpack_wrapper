package data.remote.http.responses.nova

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.nova.items.FlavorResponseJsonItem

data class ListOfFlavorsResponse(
    @SerializedName("flavors")
    val flavors: List<FlavorResponseJsonItem>? = null
)