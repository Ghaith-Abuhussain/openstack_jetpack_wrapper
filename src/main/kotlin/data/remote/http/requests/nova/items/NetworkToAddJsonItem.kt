package data.remote.http.requests.nova.items
import com.google.gson.annotations.SerializedName

data class NetworkToAddJsonItem(
    @SerializedName("uuid")
    val uuid: String? = null,
)
