package data.remote.http.requests.keystone.items

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class PasswordJsonItem(
    @SerializedName("user")
    val user: UserJsonItem? = null,
)