package data.remote.http.requests.nova
import com.google.gson.annotations.SerializedName
import data.remote.http.requests.nova.items.NewFlavorJsonItem

data class CreateNewFlavorRequest(
    @SerializedName("flavor")
    val flavor: NewFlavorJsonItem? = null,
)
