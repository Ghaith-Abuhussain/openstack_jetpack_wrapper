package presentation.viewmodels

import data.local.holder.RepositoriesHolder
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.models.Network
import domain.models.Router
import domain.models.Volume
import domain.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Route
import presentation.states.ApiCallStatus
import presentation.states.NeutronPageState

class NeutronViewModel : ViewModel() {
    private val _neutronPageState: MutableStateFlow<NeutronPageState> = MutableStateFlow(NeutronPageState())
    val neutronPageState: StateFlow<NeutronPageState> = _neutronPageState

    fun setShowToast(show: Boolean) {
        _neutronPageState.value = _neutronPageState.value.copy(showToast = show)
    }

    fun setShowLoading(showLoading: Boolean) {
        _neutronPageState.value = _neutronPageState.value.copy(showLoading = showLoading)
    }

    fun setNetworkListCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(networkListCallStatus = apiCallStatus)
    }

    fun setRouterListCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(routerListCallStatus = apiCallStatus)
    }

    fun setCreateRouterCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(createRouterCallStatus = apiCallStatus)
    }

    fun setCreateNetworkCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(createNetworkCallStatus = apiCallStatus)
    }

    fun setCreateSubnetCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(createSubnetCallStatus = apiCallStatus)
    }

    fun setDeleteNetworkCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(deleteNetworkCallStatus = apiCallStatus)
    }

    fun setDeleteRouterCallStatus(apiCallStatus: ApiCallStatus) {
        _neutronPageState.value = _neutronPageState.value.copy(deleteRouterCallStatus = apiCallStatus)
    }

    fun setSelectedRouter(router: Router) {
        router.id?.let { id ->
            RepositoriesHolder.sharedPreferencesRepository.setSelectedRouterIdValue(id)
            _neutronPageState.value = _neutronPageState.value.copy(selectedRouter = router)
        }

    }

    fun setSelectedNetwork(network: Network) {
        network.id?.let { id ->
            RepositoriesHolder.sharedPreferencesRepository.setSelectedNetworkIdValue(id)
            _neutronPageState.value = _neutronPageState.value.copy(selectedNetwork = network)
        }
    }

    fun retrieveNetworks() {
        println("Call for networks")
        _neutronPageState.value = _neutronPageState.value.copy(networkListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.getAllNetworks()) {
                is NetworkResult.Error -> {
                    println("Network Error: ${response.message}")
                    _neutronPageState.value = _neutronPageState.value.copy(
                        networkListCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    println("Exception")
                    _neutronPageState.value = _neutronPageState.value.copy(
                        networkListCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    val listOfRetrievedNetworks = response.data.networks?.map { network ->
                        Network(id = network.id, name = network.name)
                    }?.toList() ?: listOf()
                    listOfRetrievedNetworks.first().id?.let { id ->
                        RepositoriesHolder.sharedPreferencesRepository.setSelectedNetworkIdValue(id)
                    }
                    println(listOfRetrievedNetworks)
                    _neutronPageState.value = _neutronPageState.value.copy(
                        networkListCallStatus = ApiCallStatus.SUCCESS,
                        listOfRetrievedNetworks = listOfRetrievedNetworks
                    )
                }
            }
        }
    }

    fun deleteNetwork(networkId: String) {
        _neutronPageState.value = _neutronPageState.value.copy(deleteNetworkCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.deleteNetwork(networkId)) {
                is NetworkResult.Error -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        deleteNetworkCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    println("Exception")
                    _neutronPageState.value = _neutronPageState.value.copy(
                        deleteNetworkCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        deleteNetworkCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Successfully deleted network with ID:\n$networkId"
                    )
                }
            }
        }
    }

    fun deleteRouter(routerId: String) {
        _neutronPageState.value = _neutronPageState.value.copy(deleteNetworkCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.deleteRouter(routerId)) {
                is NetworkResult.Error -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        deleteNetworkCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    println("Exception")
                    _neutronPageState.value = _neutronPageState.value.copy(
                        deleteNetworkCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        deleteNetworkCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Successfully deleted router with ID:\n$routerId"
                    )
                }
            }
        }
    }

    fun retrieveRouters() {
        _neutronPageState.value = _neutronPageState.value.copy(routerListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.getAllRouters()) {
                is NetworkResult.Error -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        routerListCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        routerListCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    val listOfReceivedRouters = response.data.routers?.map { router ->
                        Router(id = router.id, name = router.name)
                    }?.toList() ?: listOf()
                    listOfReceivedRouters.first().id?.let { id ->
                        RepositoriesHolder.sharedPreferencesRepository.setSelectedRouterIdValue(id)
                    }
                    _neutronPageState.value = _neutronPageState.value.copy(
                        routerListCallStatus = ApiCallStatus.SUCCESS,
                        listOfRetrievedRouters = listOfReceivedRouters
                    )
                }
            }
        }
    }

    fun createRouter(name: String, adminStateUp: Boolean) {
        _neutronPageState.value = _neutronPageState.value.copy(createRouterCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response =
                RepositoriesHolder.openstackRepository.createNewRouter(name = name, adminStateUp = adminStateUp)) {
                is NetworkResult.Error -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createRouterCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createRouterCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createRouterCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Success Creating Router"
                    )
                }
            }
        }
    }

    fun createNetwork(name: String, adminStateUp: Boolean, isExternal: Boolean) {
        _neutronPageState.value = _neutronPageState.value.copy(createNetworkCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.createNewNetwork(
                name = name,
                adminStateUp = adminStateUp,
                isExternal = isExternal
            )) {
                is NetworkResult.Error -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createNetworkCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createNetworkCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createNetworkCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Success Creating Network"
                    )
                }
            }
        }
    }

    fun createSubnet(ipPool: String, name: String, dnsServer: String) {
        val selectedNetwork = RepositoriesHolder.sharedPreferencesRepository.getSelectedNetworkIdValue()
        val selectedRouter = RepositoriesHolder.sharedPreferencesRepository.getSelectedRouterIdValue()
        _neutronPageState.value = _neutronPageState.value.copy(createSubnetCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.createNewSubnet(
                networkId = selectedNetwork,
                ipPool = ipPool,
                name = name,
                dnsNameServer = dnsServer,
                ipVersion = 4
            )) {
                is NetworkResult.Error -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createSubnetCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _neutronPageState.value = _neutronPageState.value.copy(
                        createSubnetCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    response.data.subnet?.id?.let { subnetId ->
                        when (val response2 = RepositoriesHolder.openstackRepository.addSubnetToRouter(
                            routerId = selectedRouter,
                            subnetId = subnetId
                        )) {
                            is NetworkResult.Error -> {
                                _neutronPageState.value = _neutronPageState.value.copy(
                                    createSubnetCallStatus = ApiCallStatus.FAILED,
                                    errorCode = response2.code,
                                    errorMessage = response2.message
                                )
                            }
                            is NetworkResult.Exception -> {
                                _neutronPageState.value = _neutronPageState.value.copy(
                                    createSubnetCallStatus = ApiCallStatus.FAILED,
                                    errorCode = -1,
                                    errorMessage = response2.e.message
                                )
                            }
                            is NetworkResult.Success -> {
                                _neutronPageState.value = _neutronPageState.value.copy(
                                    createSubnetCallStatus = ApiCallStatus.SUCCESS,
                                    errorMessage = "Success Creating Subnet"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}