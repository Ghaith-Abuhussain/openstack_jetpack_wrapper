package domain.repository.remote

import data.remote.http.responses.cinder.CreateVolumeResponse
import data.remote.http.responses.cinder.ListOfVolumesResponse
import data.remote.http.responses.glance.ListOfImagesResponse
import data.remote.http.responses.keystone.AuthResponse
import data.remote.http.responses.keystone.ListOfProjectsResponse
import data.remote.http.responses.neutron.AddSubnetToRouterResponse
import data.remote.http.responses.neutron.CreateNetworkResponse
import data.remote.http.responses.neutron.CreateRouterResponse
import data.remote.http.responses.neutron.CreateSubnetResponse
import data.remote.http.responses.neutron.ListOfNetworksResponse
import data.remote.http.responses.neutron.ListOfRoutersResponse
import data.remote.http.responses.nova.CreateFlavorResponse
import data.remote.http.responses.nova.ListOfFlavorsResponse
import data.remote.http.responses.nova.ListOfInstancesResponse
import domain.utils.NetworkResult

interface OpenStackNetworkRepository {
    suspend fun authenticate(projectName: String, username: String, password: String): NetworkResult<AuthResponse>

    suspend fun getAllProjects(): NetworkResult<ListOfProjectsResponse>

    suspend fun getAllVolumes(projectId: String): NetworkResult<ListOfVolumesResponse>

    suspend fun deleteVolume(projectId: String, volumeId: String): NetworkResult<Boolean>

    suspend fun getAllFlavors(projectId: String): NetworkResult<ListOfFlavorsResponse>

    suspend fun deleteFlavor(flavorId: String): NetworkResult<Boolean>

    suspend fun getAllNetworks(): NetworkResult<ListOfNetworksResponse>

    suspend fun deleteNetwork(networkId: String): NetworkResult<Boolean>

    suspend fun getAllRouters(): NetworkResult<ListOfRoutersResponse>

    suspend fun deleteRouter(routerId: String): NetworkResult<Boolean>

    suspend fun createVolumeFromExistingOne(projectId: String, name: String, size: Int, existingVolumeId: String, description: String): NetworkResult<CreateVolumeResponse>

    suspend fun createVolume(projectId: String, name: String, size: Int, description: String): NetworkResult<CreateVolumeResponse>

    suspend fun addSubnetToRouter(routerId: String, subnetId: String): NetworkResult<AddSubnetToRouterResponse>

    suspend fun createNewRouter(name: String, adminStateUp: Boolean): NetworkResult<CreateRouterResponse>

    suspend fun createNewNetwork(name: String, adminStateUp: Boolean, isExternal: Boolean): NetworkResult<CreateNetworkResponse>

    suspend fun createNewSubnet(networkId: String, ipVersion: Int, ipPool: String, name: String, dnsNameServer: String): NetworkResult<CreateSubnetResponse>

    suspend fun addNewFlavor(name: String, ram: Int, disk: Int): NetworkResult<CreateFlavorResponse>

    suspend fun getAllInstances(projectId: String): NetworkResult<ListOfInstancesResponse>

    suspend fun deleteInstances(instanceId: String): NetworkResult<Boolean>

    suspend fun bootVm(projectId: String, name: String, flavorId: String, volumeId: String, networkId: String): NetworkResult<Boolean>

    suspend fun getAllImages(): NetworkResult<ListOfImagesResponse>
}