package data.remote.http.requests.neutron.items
import com.google.gson.annotations.SerializedName

data class SubnetJsonItem(
    @SerializedName("network_id")
    val networkId: String? = null,

    @SerializedName("ip_version")
    val ipVersion: Int? = null,

    @SerializedName("cidr")
    val ipPool: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("dns_nameservers")
    val dnsNameServers: List<String>? = null,
)
