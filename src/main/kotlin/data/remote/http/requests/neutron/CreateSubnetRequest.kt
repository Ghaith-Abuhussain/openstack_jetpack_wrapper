package data.remote.http.requests.neutron

import com.google.gson.annotations.SerializedName
import data.remote.http.requests.neutron.items.SubnetJsonItem

data class CreateSubnetRequest(
    @SerializedName("subnet")
    val subnet: SubnetJsonItem? = null,
)
