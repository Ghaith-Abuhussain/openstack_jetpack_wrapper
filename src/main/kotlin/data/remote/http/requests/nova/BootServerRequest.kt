package data.remote.http.requests.nova

import com.google.gson.annotations.SerializedName
import data.remote.http.requests.nova.items.ServerJsonItem

data class BootServerRequest(
    @SerializedName("server")
    val server: ServerJsonItem? = null,
)