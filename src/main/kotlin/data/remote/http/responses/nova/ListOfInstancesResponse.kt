package data.remote.http.responses.nova

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.nova.items.InstanceResponseJsonItem

data class ListOfInstancesResponse(
    @SerializedName("servers")
    val servers: List<InstanceResponseJsonItem>? = null,
)
