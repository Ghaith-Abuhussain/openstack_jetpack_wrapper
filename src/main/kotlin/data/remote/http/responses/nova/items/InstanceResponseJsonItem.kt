package data.remote.http.responses.nova.items

import com.google.gson.annotations.SerializedName

data class InstanceResponseJsonItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,
)
