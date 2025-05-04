package data.remote.http.requests.neutron
import com.google.gson.annotations.SerializedName
import data.remote.http.requests.neutron.items.RouterJsonItem

data class CreateRouterRequest(
    @SerializedName("router")
    val router: RouterJsonItem? = null,
)
