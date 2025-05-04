package presentation.viewmodels

import data.local.holder.RepositoriesHolder
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.models.Flavor
import domain.models.Router
import domain.models.Server
import domain.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.states.ApiCallStatus
import presentation.states.NovaPageState

class NovaViewModel: ViewModel() {

    private val _novaPageState = MutableStateFlow<NovaPageState>(NovaPageState())
    val novaPageState: StateFlow<NovaPageState> = _novaPageState

    fun setFlavorListCallStatus(apiCallStatus: ApiCallStatus) {
        _novaPageState.value = _novaPageState.value.copy(flavorListCallStatus = apiCallStatus)
    }

    fun setInstanceListCallStatus(apiCallStatus: ApiCallStatus) {
        _novaPageState.value = _novaPageState.value.copy(instanceListCallStatus = apiCallStatus)
    }

    fun setCreateFlavorCallStatus(apiCallStatus: ApiCallStatus) {
        _novaPageState.value = _novaPageState.value.copy(createFlavorCallStatus = apiCallStatus)
    }

    fun setBootVMCallStatus(apiCallStatus: ApiCallStatus) {
        _novaPageState.value = _novaPageState.value.copy(bootVMCallStatus = apiCallStatus)
    }

    fun setDeleteFlavorCallStatus(apiCallStatus: ApiCallStatus) {
        _novaPageState.value = _novaPageState.value.copy(deleteFlavorCallStatus = apiCallStatus)
    }

    fun setDeleteInstanceCallStatus(apiCallStatus: ApiCallStatus) {
        _novaPageState.value = _novaPageState.value.copy(deleteInstanceCallStatus = apiCallStatus)
    }


    fun setShowToast(show: Boolean) {
        _novaPageState.value = _novaPageState.value.copy(showToast = show)
    }

    fun setShowLoading(showLoading: Boolean) {
        _novaPageState.value = _novaPageState.value.copy(showLoading = showLoading)
    }

    fun setSelectedFlavor(flavor: Flavor) {
        flavor.id?.let { id ->
            RepositoriesHolder.sharedPreferencesRepository.setSelectedFlavorIdValue(id)
            _novaPageState.value = _novaPageState.value.copy(selectedFlavor = flavor)
        }
    }

    fun retrieveFlavors() {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        _novaPageState.value = _novaPageState.value.copy(flavorListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.getAllFlavors(projectId)) {
                is NetworkResult.Error -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        flavorListCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        flavorListCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    val listOfRetrievedFlavors = response.data.flavors?.map { flavor ->
                        Flavor(id = flavor.id, name = flavor.name)
                    }?.toList() ?: listOf()
                    listOfRetrievedFlavors.first().id?.let { id ->
                        RepositoriesHolder.sharedPreferencesRepository.setSelectedFlavorIdValue(id)
                    }
                    _novaPageState.value = _novaPageState.value.copy(
                        flavorListCallStatus = ApiCallStatus.SUCCESS,
                        listOfRetrievedFlavors = listOfRetrievedFlavors
                    )
                }
            }
        }
    }

    fun retrieveInstances() {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        _novaPageState.value = _novaPageState.value.copy(instanceListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = RepositoriesHolder.openstackRepository.getAllInstances(projectId)) {
                is NetworkResult.Error -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        instanceListCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        instanceListCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    val listOfRetrievedInstances = response.data.servers?.map { server ->
                        Server(id = server.id, name = server.name)
                    }?.toList() ?: listOf()
                    _novaPageState.value = _novaPageState.value.copy(
                        instanceListCallStatus = ApiCallStatus.SUCCESS,
                        listOfRetrievedServers = listOfRetrievedInstances
                    )
                }
            }
        }
    }

    fun createFlavor(name: String, ram: Int, disk: Int) {
        _novaPageState.value = _novaPageState.value.copy(createFlavorCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response =
                RepositoriesHolder.openstackRepository.addNewFlavor(name = name, ram = ram, disk = disk)) {
                is NetworkResult.Error -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        createFlavorCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        createFlavorCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        createFlavorCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Success Creating Flavor"
                    )
                }
            }
        }
    }

    fun deleteFlavor(flavorId: String) {
        _novaPageState.value = _novaPageState.value.copy(deleteFlavorCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response =
                RepositoriesHolder.openstackRepository.deleteFlavor(flavorId = flavorId)) {
                is NetworkResult.Error -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        deleteFlavorCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        deleteFlavorCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        deleteFlavorCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Success Deleting Flavor with ID:\n$flavorId"
                    )
                }
            }
        }
    }

    fun deleteInstance(instanceId: String) {
        _novaPageState.value = _novaPageState.value.copy(deleteInstanceCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response =
                RepositoriesHolder.openstackRepository.deleteInstances(instanceId = instanceId)) {
                is NetworkResult.Error -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        deleteInstanceCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        deleteInstanceCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        deleteInstanceCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Success Deleting Server with ID:\n$instanceId"
                    )
                }
            }
        }
    }

    fun bootVM(name: String) {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        val volumeId = RepositoriesHolder.sharedPreferencesRepository.getSelectedVolumeIdValue()
        val flavorId = RepositoriesHolder.sharedPreferencesRepository.getSelectedFlavorIdValue()
        val networkId = RepositoriesHolder.sharedPreferencesRepository.getSelectedNetworkIdValue()

        _novaPageState.value = _novaPageState.value.copy(bootVMCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch {
            when (val response =
                RepositoriesHolder.openstackRepository.bootVm(name = name, projectId = projectId, volumeId = volumeId, flavorId = flavorId, networkId = networkId)) {
                is NetworkResult.Error -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        bootVMCallStatus = ApiCallStatus.FAILED,
                        errorCode = response.code,
                        errorMessage = response.message
                    )
                }

                is NetworkResult.Exception -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        bootVMCallStatus = ApiCallStatus.FAILED,
                        errorCode = -1,
                        errorMessage = response.e.message
                    )
                }

                is NetworkResult.Success -> {
                    _novaPageState.value = _novaPageState.value.copy(
                        bootVMCallStatus = ApiCallStatus.SUCCESS,
                        errorMessage = "Success Boot VM"
                    )
                }
            }
        }
    }
}