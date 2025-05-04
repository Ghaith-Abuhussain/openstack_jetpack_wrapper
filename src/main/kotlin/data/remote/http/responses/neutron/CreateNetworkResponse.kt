package data.remote.http.responses.neutron

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.neutron.items.NetworkResponseJsonItem

data class CreateNetworkResponse(
    @SerializedName("network")
    val network: NetworkResponseJsonItem? = null,
)
