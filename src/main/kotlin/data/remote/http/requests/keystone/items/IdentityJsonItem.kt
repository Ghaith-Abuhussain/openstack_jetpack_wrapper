package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class IdentityJsonItem(
    @SerializedName("methods")
    val methods: List<String>? = null,

    @SerializedName("password")
    val password: PasswordJsonItem? = null,
)
