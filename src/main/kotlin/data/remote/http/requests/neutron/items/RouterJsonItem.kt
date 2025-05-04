package data.remote.http.requests.neutron.items

import com.google.gson.annotations.SerializedName

data class RouterJsonItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("admin_state_up")
    val adminStateUp: Boolean? = true,
)