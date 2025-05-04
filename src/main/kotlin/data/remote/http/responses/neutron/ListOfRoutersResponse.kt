package data.remote.http.responses.neutron
import com.google.gson.annotations.SerializedName
import data.remote.http.responses.neutron.items.RouterResponseJsonItem

data class ListOfRoutersResponse(
    @SerializedName("routers")
    val routers: List<RouterResponseJsonItem>? = null
)