package data.remote.http.responses.neutron.items

import com.google.gson.annotations.SerializedName

data class RouterResponseJsonItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("status")
    val status: String? = null,
)