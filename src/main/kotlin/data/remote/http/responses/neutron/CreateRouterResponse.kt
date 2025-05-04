package data.remote.http.responses.neutron

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.neutron.items.RouterResponseJsonItem

data class CreateRouterResponse(
    @SerializedName("router")
    val router: RouterResponseJsonItem? = null,
)
