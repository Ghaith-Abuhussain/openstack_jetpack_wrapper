package data.local.repository

import androidx.compose.runtime.key
import data.local.SharedPreferencesDefaultValues.AUTH_TOKEN_DEFAULT_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_AUTH_LOGIN_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_AUTH_PASSWORD_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_CINDER_RETROFIT_URL_KEY_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_KEYSTONE_RETROFIT_URL_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_NEUTRON_RETROFIT_URL_KEY_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_NOVA_RETROFIT_URL_KEY_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_RETROFIT_URL_KEY_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_FLAVOR_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_IMAGE_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_NETWORK_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_PROJECT_ID_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_ROUTER_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_SUBNET_VALUE
import data.local.SharedPreferencesDefaultValues.DEFAULT_SELECTED_VOLUME_VALUE
import data.local.SharedPreferencesKeys.AUTH_LOGIN_KEY
import data.local.SharedPreferencesKeys.AUTH_PASSWORD_KEY
import data.local.SharedPreferencesKeys.AUTH_TOKEN_KEY
import data.local.SharedPreferencesKeys.CINDER_RETROFIT_URL_KEY
import data.local.SharedPreferencesKeys.GLANCE_RETROFIT_URL_KEY
import data.local.SharedPreferencesKeys.KEYSTONE_RETROFIT_URL_KEY
import data.local.SharedPreferencesKeys.NEUTRON_RETROFIT_URL_KEY
import data.local.SharedPreferencesKeys.NOVA_RETROFIT_URL_KEY
import data.local.SharedPreferencesKeys.SELECTED_FLAVOR_KEY
import data.local.SharedPreferencesKeys.SELECTED_IMAGE_KEY
import data.local.SharedPreferencesKeys.SELECTED_NETWORK_KEY
import data.local.SharedPreferencesKeys.SELECTED_PROJECT_ID_KEY
import data.local.SharedPreferencesKeys.SELECTED_ROUTER_KEY
import data.local.SharedPreferencesKeys.SELECTED_SUBNET_KEY
import data.local.SharedPreferencesKeys.SELECTED_VOLUME_KEY
import data.local.preferences.Preferences
import domain.repository.local.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl: SharedPreferencesRepository {
    override fun getKeystoneUrl(): String {
        return Preferences.getString(key = KEYSTONE_RETROFIT_URL_KEY, default = DEFAULT_KEYSTONE_RETROFIT_URL_VALUE)
    }

    override fun setKeystoneUrl(url: String) {
        Preferences.set(key = KEYSTONE_RETROFIT_URL_KEY, url)
    }

    override fun getCinderUrl(): String {
        return Preferences.getString(key = CINDER_RETROFIT_URL_KEY, default = DEFAULT_CINDER_RETROFIT_URL_KEY_VALUE)
    }

    override fun setCinderUrl(url: String) {
        Preferences.set(key = CINDER_RETROFIT_URL_KEY, url)
    }

    override fun getNovaRetrofitUrl(): String {
        return Preferences.getString(key = NOVA_RETROFIT_URL_KEY, default = DEFAULT_NOVA_RETROFIT_URL_KEY_VALUE)
    }

    override fun setNovaRetrofitUrl(url: String) {
        Preferences.set(key = NOVA_RETROFIT_URL_KEY, url)
    }

    override fun getNeutronRetrofitUrl(): String {
        return Preferences.getString(key = NEUTRON_RETROFIT_URL_KEY, default = DEFAULT_NEUTRON_RETROFIT_URL_KEY_VALUE)
    }

    override fun setNeutronRetrofitUrl(url: String) {
        Preferences.set(key = NEUTRON_RETROFIT_URL_KEY, url)
    }

    override fun getGlanceRetrofitUrl(): String {
        return Preferences.getString(key = GLANCE_RETROFIT_URL_KEY, default = DEFAULT_RETROFIT_URL_KEY_VALUE)
    }

    override fun setGlanceRetrofitUrl(url: String) {
        Preferences.set(key = GLANCE_RETROFIT_URL_KEY, url)
    }

    override fun getAuthTokenValue(): String {
        return Preferences.getString(key = AUTH_TOKEN_KEY, default = AUTH_TOKEN_DEFAULT_VALUE)
    }

    override fun setAuthTokenValue(token: String) {
        Preferences.set(key = AUTH_TOKEN_KEY, token)
    }

    override fun getAuthLoginValue(): String {
        return Preferences.getString(key = AUTH_LOGIN_KEY, default = DEFAULT_AUTH_LOGIN_VALUE)
    }

    override fun setAuthLoginValue(login: String) {
        Preferences.set(key = AUTH_LOGIN_KEY, login)
    }

    override fun getAuthPasswordValue(): String {
        return Preferences.getString(key = AUTH_PASSWORD_KEY, default = DEFAULT_AUTH_PASSWORD_VALUE)
    }

    override fun setAuthPasswordValue(password: String) {
        Preferences.set(key = AUTH_PASSWORD_KEY, password)
    }

    override fun getSelectedProjectIdValue(): String {
        return Preferences.getString(key = SELECTED_PROJECT_ID_KEY, DEFAULT_SELECTED_PROJECT_ID_VALUE)
    }

    override fun setSelectedProjectIdValue(projectId: String) {
        Preferences.set(key = SELECTED_PROJECT_ID_KEY, projectId)
    }

    override fun getSelectedVolumeIdValue(): String {
        return Preferences.getString(key = SELECTED_VOLUME_KEY, default = DEFAULT_SELECTED_VOLUME_VALUE)
    }

    override fun setSelectedVolumeIdValue(volumeId: String) {
        Preferences.set(key = SELECTED_VOLUME_KEY, volumeId)
    }

    override fun getSelectedFlavorIdValue(): String {
        return Preferences.getString(key = SELECTED_FLAVOR_KEY, default = DEFAULT_SELECTED_FLAVOR_VALUE)
    }

    override fun setSelectedFlavorIdValue(flavorId: String) {
        Preferences.set(key = SELECTED_FLAVOR_KEY, flavorId)
    }

    override fun getSelectedNetworkIdValue(): String {
        return Preferences.getString(key = SELECTED_NETWORK_KEY, default = DEFAULT_SELECTED_NETWORK_VALUE)
    }

    override fun setSelectedNetworkIdValue(networkId: String) {
        Preferences.set(key = SELECTED_NETWORK_KEY, networkId)
    }

    override fun getSelectedSubnetIdValue(): String {
        return Preferences.getString(key = SELECTED_SUBNET_KEY, DEFAULT_SELECTED_SUBNET_VALUE)
    }

    override fun setSelectedSubnetIdValue(subnetId: String) {
        Preferences.set(key = SELECTED_SUBNET_KEY, subnetId)
    }

    override fun getSelectedRouterIdValue(): String {
        return Preferences.getString(key = SELECTED_ROUTER_KEY, DEFAULT_SELECTED_ROUTER_VALUE)
    }

    override fun setSelectedRouterIdValue(routerId: String) {
        Preferences.set(key = SELECTED_ROUTER_KEY, routerId)
    }

    override fun getSelectedImageIdValue(): String {
        return Preferences.getString(key = SELECTED_IMAGE_KEY, default = DEFAULT_SELECTED_IMAGE_VALUE)
    }

    override fun setSelectedImageIdValue(imageId: String) {
        Preferences.set(key = SELECTED_IMAGE_KEY, imageId)
    }
}