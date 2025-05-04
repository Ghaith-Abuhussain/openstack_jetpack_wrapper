package presentation.states

import domain.models.Flavor
import domain.models.Server

data class NovaPageState(
    val errorCode: Int? = -1,
    val errorMessage: String? = null,
    val flavorListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val instanceListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val createFlavorCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val deleteFlavorCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val deleteInstanceCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val bootVMCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val selectedFlavor: Flavor = Flavor(id = null, name = null),
    val listOfRetrievedFlavors: List<Flavor> = listOf(),
    val listOfRetrievedServers: List<Server> = listOf(),
    val showToast: Boolean = false,
    val showLoading: Boolean = false
)
