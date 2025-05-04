package data.remote.http.responses.neutron

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.neutron.items.NetworkResponseJsonItem

data class ListOfNetworksResponse(
    @SerializedName("networks")
    val networks: List<NetworkResponseJsonItem>? = null,
)