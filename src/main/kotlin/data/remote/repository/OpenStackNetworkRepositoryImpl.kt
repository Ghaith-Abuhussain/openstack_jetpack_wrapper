package data.remote.repository

import data.local.holder.RepositoriesHolder
import data.remote.http.requests.cinder.CreateVolumeRequest
import data.remote.http.requests.cinder.items.ExistingVolumeJsonItem
import data.remote.http.requests.cinder.items.NewVolumeJsonItem
import data.remote.http.requests.keystone.KeystoneAuthenticationRequest
import data.remote.http.requests.keystone.items.AuthJsonItem
import data.remote.http.requests.keystone.items.DomainJsonItem
import data.remote.http.requests.keystone.items.IdentityJsonItem
import data.remote.http.requests.keystone.items.PasswordJsonItem
import data.remote.http.requests.keystone.items.ProjectJsonItem
import data.remote.http.requests.keystone.items.ScopeJsonItem
import data.remote.http.requests.keystone.items.UserJsonItem
import data.remote.http.requests.neutron.AddSubnetToRouterRequest
import data.remote.http.requests.neutron.CreateNetworkRequest
import data.remote.http.requests.neutron.CreateRouterRequest
import data.remote.http.requests.neutron.CreateSubnetRequest
import data.remote.http.requests.neutron.items.NetworkJsonItem
import data.remote.http.requests.neutron.items.RouterJsonItem
import data.remote.http.requests.neutron.items.SubnetJsonItem
import data.remote.http.requests.nova.BootServerRequest
import data.remote.http.requests.nova.CreateNewFlavorRequest
import data.remote.http.requests.nova.items.BlockDeviceMappingJsonItem
import data.remote.http.requests.nova.items.NetworkToAddJsonItem
import data.remote.http.requests.nova.items.NewFlavorJsonItem
import data.remote.http.requests.nova.items.ServerJsonItem
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
import data.remote.retrofit.builder.OpenStackRetrofitBuilder
import domain.repository.remote.OpenStackNetworkRepository
import domain.utils.NetworkResult
import sun.security.jgss.GSSUtil.login
import java.lang.Exception

class OpenStackNetworkRepositoryImpl : OpenStackNetworkRepository {
    override suspend fun authenticate(
        projectName: String,
        username: String,
        password: String
    ): NetworkResult<AuthResponse> {
        val keystoneAuthenticationRequest = KeystoneAuthenticationRequest(
            auth = AuthJsonItem(
                identity = IdentityJsonItem(
                    methods = listOf("password"),
                    password = PasswordJsonItem(
                        user = UserJsonItem(
                            name = username,
                            password = password,
                            domain = DomainJsonItem(
                                name = "Default"
                            )
                        )
                    )
                ),
                scope = ScopeJsonItem(
                    project = ProjectJsonItem(
                        name = projectName,
                        domain = DomainJsonItem(
                            name = "Default"
                        )
                    )
                ),
            )
        )
        println(keystoneAuthenticationRequest)
        return try {
            val response = OpenStackRetrofitBuilder.keyStoneApi.authenticate(keystoneAuthenticationRequest)
            if (response.isSuccessful) {
                val token = response.headers()["X-Subject-Token"]
                token?.let {
                    RepositoriesHolder.sharedPreferencesRepository.setAuthTokenValue(token)
                    RepositoriesHolder.sharedPreferencesRepository.setAuthLoginValue(username)
                    RepositoriesHolder.sharedPreferencesRepository.setAuthPasswordValue(password)
                }
                NetworkResult.Success(
                    AuthResponse(
                        token = token,
                        issuedAt = response.body()?.issuedAt,
                        expiresAt = response.body()?.expiresAt
                    )
                )
            } else {
                NetworkResult.Error(code = response.code(), message = response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        }
    }

    override suspend fun getAllProjects(): NetworkResult<ListOfProjectsResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.keyStoneApi.getProjects(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun getAllVolumes(projectId: String): NetworkResult<ListOfVolumesResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.cinderApi.getListOfVolumes(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun deleteVolume(
        projectId: String,
        volumeId: String
    ): NetworkResult<Boolean> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.cinderApi.deleteVolume(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId,
                    volumeId = volumeId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun getAllFlavors(projectId: String): NetworkResult<ListOfFlavorsResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.novaApi.getFlavors(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun deleteFlavor(flavorId: String): NetworkResult<Boolean> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.novaApi.deleteFlavor(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    flavorId = flavorId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun getAllNetworks(): NetworkResult<ListOfNetworksResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.getNetworks(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun deleteNetwork(networkId: String): NetworkResult<Boolean> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.deleteNetwork(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    networkId = networkId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun getAllRouters(): NetworkResult<ListOfRoutersResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.getRouters(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun deleteRouter(routerId: String): NetworkResult<Boolean> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.deleteRouter(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    routerId = routerId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun createVolumeFromExistingOne(
        projectId: String,
        name: String,
        size: Int,
        existingVolumeId: String,
        description: String
    ): NetworkResult<CreateVolumeResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val createVolumeRequest = CreateVolumeRequest<ExistingVolumeJsonItem>(
            volume = ExistingVolumeJsonItem(
                name = name,
                description = description,
                size = size,
                sourceVolid = existingVolumeId
            )
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.cinderApi.createVolumeFromExistingOne(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId,
                    createVolume = createVolumeRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun createVolume(
        projectId: String,
        name: String,
        size: Int,
        description: String
    ): NetworkResult<CreateVolumeResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val imageId = RepositoriesHolder.sharedPreferencesRepository.getSelectedImageIdValue()
        val createVolumeRequest = CreateVolumeRequest<NewVolumeJsonItem>(
            volume = NewVolumeJsonItem(
                name = name,
                description = description,
                size = size,
                imageRef = imageId
            )
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.cinderApi.createVolume(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId,
                    createVolume = createVolumeRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun addSubnetToRouter(
        routerId: String,
        subnetId: String
    ): NetworkResult<AddSubnetToRouterResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val addSubnetToRouterRequest = AddSubnetToRouterRequest(
            subnetId = subnetId,
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.addSubNetToRouter(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    routerId = routerId,
                    body = addSubnetToRouterRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun createNewRouter(
        name: String,
        adminStateUp: Boolean
    ): NetworkResult<CreateRouterResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val createRouterRequest = CreateRouterRequest(
            router = RouterJsonItem(
                name = name,
                adminStateUp = adminStateUp,
            ),
        )
        println(createRouterRequest)
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.createNewRouter(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    body = createRouterRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun createNewNetwork(
        name: String,
        adminStateUp: Boolean,
        isExternal: Boolean
    ): NetworkResult<CreateNetworkResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val createNetworkRequest = CreateNetworkRequest(
            network = NetworkJsonItem(
                name = name,
                adminStateUp = adminStateUp,
                routerExternal = isExternal,
            ),
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.createNetwork(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    body = createNetworkRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun createNewSubnet(
        networkId: String,
        ipVersion: Int,
        ipPool: String,
        name: String,
        dnsNameServer: String
    ): NetworkResult<CreateSubnetResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val createSubnetRequest = CreateSubnetRequest(
            subnet = SubnetJsonItem(
                name = name,
                networkId = networkId,
                ipVersion = ipVersion,
                ipPool = ipPool,
                dnsNameServers = listOf(dnsNameServer)
            ),
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.neutronApi.createSubnet(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    body = createSubnetRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    println(response.message())
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun addNewFlavor(
        name: String,
        ram: Int,
        disk: Int
    ): NetworkResult<CreateFlavorResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val addFlavorRequest = CreateNewFlavorRequest(
            flavor = NewFlavorJsonItem(
                name = name,
                ram = ram,
                disk = disk,
            ),
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.novaApi.addFlavor(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    createNewFlavorRequest = addFlavorRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun getAllInstances(projectId: String): NetworkResult<ListOfInstancesResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.novaApi.getInstances(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId,
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun deleteInstances(instanceId: String): NetworkResult<Boolean> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.novaApi.deleteInstance(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId,
                    serverId = instanceId
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun bootVm(
        projectId: String,
        name: String,
        flavorId: String,
        volumeId: String,
        networkId: String
    ): NetworkResult<Boolean> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val bootVmRequest = BootServerRequest(
            server = ServerJsonItem(
                name = name,
                flavorRef = flavorId,
                blockDeviceMappingV2 = listOf(
                    BlockDeviceMappingJsonItem(
                        uuid = volumeId
                    )
                ),
                networks = listOf(
                    NetworkToAddJsonItem(
                        uuid = networkId,
                    )
                )
            ),
        )
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.novaApi.bootServer(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                    projectId = projectId,
                    bootServerRequest = bootVmRequest
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(true)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }

    override suspend fun getAllImages(): NetworkResult<ListOfImagesResponse> {
        val xAuthToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        val xSubjectToken = RepositoriesHolder.sharedPreferencesRepository.getAuthTokenValue()
        return if (xSubjectToken.isNotEmpty()) {
            try {
                val response = OpenStackRetrofitBuilder.glanceApi.getImages(
                    xAuthToken = xAuthToken,
                    xSubjectToken = xSubjectToken,
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(code = response.code(), message = response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Exception(e)
            }
        } else {
            NetworkResult.Exception(Throwable("No Token"))
        }
    }
}