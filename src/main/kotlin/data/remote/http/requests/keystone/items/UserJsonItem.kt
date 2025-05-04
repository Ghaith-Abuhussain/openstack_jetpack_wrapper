package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName

data class UserJsonItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("domain")
    val domain: DomainJsonItem? = null,

    @SerializedName("password")
    val password: String? = null,
)
