package data.remote.http.requests.keystone

import com.google.gson.annotations.SerializedName
import data.remote.http.requests.keystone.items.AuthJsonItem

data class KeystoneAuthenticationRequest(
    @SerializedName("auth")
    val auth: AuthJsonItem? = null,
)