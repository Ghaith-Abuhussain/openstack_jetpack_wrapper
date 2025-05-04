package presentation.states

import domain.models.Network
import domain.models.Router
import domain.models.Volume

data class NeutronPageState(
    val errorCode: Int? = -1,
    val errorMessage: String? = null,
    val networkListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val routerListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val deleteNetworkCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val deleteRouterCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val createRouterCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val createNetworkCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val createSubnetCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val selectedNetwork: Network = Network(id = null, name = null),
    val selectedRouter: Router = Router(id = null, name = null),
    val listOfRetrievedNetworks: List<Network> = listOf(),
    val listOfRetrievedRouters: List<Router> = listOf(),
    val showToast: Boolean = false,
    val showLoading: Boolean = false
)