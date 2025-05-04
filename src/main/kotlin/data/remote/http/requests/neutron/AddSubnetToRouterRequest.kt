package data.remote.http.requests.neutron
import com.google.gson.annotations.SerializedName

data class AddSubnetToRouterRequest(
    @SerializedName("subnet_id")
    val subnetId: String? = null,
)
