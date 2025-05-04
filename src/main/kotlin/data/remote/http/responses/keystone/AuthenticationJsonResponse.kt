package data.remote.http.responses.keystone
import com.google.gson.annotations.SerializedName

data class AuthenticationJsonResponse(
    @SerializedName("issued_at")
    val issuedAt: String? = null,

    @SerializedName("expires_at")
    val expiresAt: String? = null,
)
