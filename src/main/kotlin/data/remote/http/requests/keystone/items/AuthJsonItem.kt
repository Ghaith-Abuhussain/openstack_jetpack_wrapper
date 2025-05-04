package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName

data class AuthJsonItem(
    @SerializedName("identity")
    val identity: IdentityJsonItem? = null,
    @SerializedName("scope")
    val scope: ScopeJsonItem? = null,
)