package domain.repository.local

import data.local.preferences.Preferences

interface SharedPreferencesRepository {

    fun getKeystoneUrl(): String
    fun setKeystoneUrl(url: String)

    fun getCinderUrl(): String
    fun setCinderUrl(url: String)

    fun getNovaRetrofitUrl(): String
    fun setNovaRetrofitUrl(url: String)

    fun getNeutronRetrofitUrl(): String
    fun setNeutronRetrofitUrl(url: String)

    fun getGlanceRetrofitUrl(): String
    fun setGlanceRetrofitUrl(url: String)

    fun getAuthTokenValue(): String
    fun setAuthTokenValue(token: String)

    fun getAuthLoginValue(): String
    fun setAuthLoginValue(login: String)

    fun getAuthPasswordValue(): String
    fun setAuthPasswordValue(password: String)

    fun getSelectedProjectIdValue(): String
    fun setSelectedProjectIdValue(projectId: String)

    fun getSelectedVolumeIdValue(): String
    fun setSelectedVolumeIdValue(volumeId: String)

    fun getSelectedFlavorIdValue(): String
    fun setSelectedFlavorIdValue(flavorId: String)

    fun getSelectedNetworkIdValue(): String
    fun setSelectedNetworkIdValue(networkId: String)

    fun getSelectedSubnetIdValue(): String
    fun setSelectedSubnetIdValue(subnetId: String)

    fun getSelectedRouterIdValue(): String
    fun setSelectedRouterIdValue(routerId: String)

    fun getSelectedImageIdValue(): String
    fun setSelectedImageIdValue(imageId: String)
}