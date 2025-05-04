package data.remote.http.requests.neutron.items
import com.google.gson.annotations.SerializedName

data class NetworkJsonItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("admin_state_up")
    val adminStateUp: Boolean? = null,

    @SerializedName("router:external")
    val routerExternal: Boolean? = null,
)
