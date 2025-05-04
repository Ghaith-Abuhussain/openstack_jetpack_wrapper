package data.remote.http.requests.neutron

import com.google.gson.annotations.SerializedName
import data.remote.http.requests.neutron.items.NetworkJsonItem

data class CreateNetworkRequest(
    @SerializedName("network")
    val network: NetworkJsonItem? = null,
)