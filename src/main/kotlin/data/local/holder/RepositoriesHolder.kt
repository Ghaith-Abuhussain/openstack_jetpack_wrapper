package data.local.holder

import data.local.repository.SharedPreferencesRepositoryImpl
import data.remote.repository.OpenStackNetworkRepositoryImpl
import domain.repository.local.SharedPreferencesRepository
import domain.repository.remote.OpenStackNetworkRepository

object RepositoriesHolder {
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository
    lateinit var openstackRepository: OpenStackNetworkRepository

    fun build() {
        sharedPreferencesRepository = SharedPreferencesRepositoryImpl()
        openstackRepository = OpenStackNetworkRepositoryImpl()
    }
}