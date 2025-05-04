package presentation.states

import domain.models.Project

data class KeyStonePageState(
    val errorCode: Int? = -1,
    val errorMessage: String? = "",
    val loginCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val projectListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val listOfRetrievedProjects: List<Project>? = listOf(),
    val showToast: Boolean = false,
    val showLoading: Boolean = false
)