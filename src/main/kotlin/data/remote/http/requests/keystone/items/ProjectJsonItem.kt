package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName

data class ProjectJsonItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("domain")
    val domain: DomainJsonItem? = null,
)