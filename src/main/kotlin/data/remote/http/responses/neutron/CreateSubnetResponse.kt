package data.remote.http.responses.neutron

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.neutron.items.SubnetResponseJsonItem

data class CreateSubnetResponse(
    @SerializedName("subnet")
    val subnet: SubnetResponseJsonItem? = null,
)