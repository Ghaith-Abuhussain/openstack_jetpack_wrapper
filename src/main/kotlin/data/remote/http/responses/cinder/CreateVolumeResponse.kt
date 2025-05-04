package data.remote.http.responses.cinder
import com.google.gson.annotations.SerializedName
import data.remote.http.responses.cinder.items.VolumeJsonItem

data class CreateVolumeResponse(
    @SerializedName("volume")
    val volume: VolumeJsonItem? = null,
)
