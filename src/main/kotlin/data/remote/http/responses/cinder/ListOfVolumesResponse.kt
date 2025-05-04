package data.remote.http.responses.cinder
import com.google.gson.annotations.SerializedName
import data.remote.http.responses.cinder.items.VolumeJsonItem

data class ListOfVolumesResponse(
    @SerializedName("volumes")
    val volumes: List<VolumeJsonItem>? = null
)