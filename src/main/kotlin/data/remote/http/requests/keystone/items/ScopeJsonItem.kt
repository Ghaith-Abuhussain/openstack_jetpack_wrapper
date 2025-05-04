package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName

data class ScopeJsonItem(
    @SerializedName("project")
    val project: ProjectJsonItem? = null,
)
