package data.remote.http.responses.neutron.items
import com.google.gson.annotations.SerializedName

data class SubnetResponseJsonItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("network_id")
    val networkId: String? = null,

    @SerializedName("project_id")
    val projectId: String? = null,

    @SerializedName("enable_dhcp")
    val enableDhcp: Boolean? = null,

    @SerializedName("ip_version")
    val ipVersion: Int? = null,

    @SerializedName("cidr")
    val cidr: String? = null,

    @SerializedName("gateway_ip")
    val gatewayIp: String? = null,
)