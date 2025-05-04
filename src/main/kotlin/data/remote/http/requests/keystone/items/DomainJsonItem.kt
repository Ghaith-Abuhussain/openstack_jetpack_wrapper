package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName

data class DomainJsonItem(
    @SerializedName("name")
    val name: String? = null
)
