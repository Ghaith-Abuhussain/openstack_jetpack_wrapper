package data.remote.http.responses.keystone

data class AuthResponse(
    val token: String? = null,
    val issuedAt: String? = null,
    val expiresAt: String? = null,
)
