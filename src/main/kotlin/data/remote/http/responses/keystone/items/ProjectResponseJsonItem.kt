package data.remote.http.responses.keystone.items

import com.google.gson.annotations.SerializedName

data class ProjectResponseJsonItem(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,
)