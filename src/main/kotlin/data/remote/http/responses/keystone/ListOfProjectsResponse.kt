package data.remote.http.responses.keystone

import com.google.gson.annotations.SerializedName
import data.remote.http.responses.keystone.items.ProjectResponseJsonItem

class ListOfProjectsResponse {
    @SerializedName("projects")
    val projects: List<ProjectResponseJsonItem>? = null
}