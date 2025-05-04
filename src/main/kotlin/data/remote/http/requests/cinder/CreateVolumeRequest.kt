package data.remote.http.requests.cinder

import com.google.gson.annotations.SerializedName

data class CreateVolumeRequest<T>(
    @SerializedName("volume")
    val volume: T? = null,
)
