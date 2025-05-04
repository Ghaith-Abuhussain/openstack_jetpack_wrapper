package presentation.viewmodels

import data.local.holder.RepositoriesHolder
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.models.Project
import domain.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import presentation.states.ApiCallStatus
import presentation.states.KeyStonePageState

class KeystoneViewModel: ViewModel() {

    private val _keystoneState = MutableStateFlow<KeyStonePageState>(KeyStonePageState())
    val keystoneState: StateFlow<KeyStonePageState> = _keystoneState

    fun setShowToast(show: Boolean) {
        _keystoneState.value = _keystoneState.value.copy(showToast = show)
    }

    fun setShowLoading(showLoading: Boolean) {
        _keystoneState.value = _keystoneState.value.copy(showLoading = showLoading)
    }

    fun setLoginApiCallingState(apiCallStatus: ApiCallStatus) {
        _keystoneState.value = _keystoneState.value.copy(loginCallStatus = apiCallStatus)
    }

    fun setProjectListApiCallingState(apiCallStatus: ApiCallStatus) {
        _keystoneState.value = _keystoneState.value.copy(projectListCallStatus = apiCallStatus)
    }

    fun login(username: String, password: String, projectName: String) {
        _keystoneState.value = _keystoneState.value.copy(loginCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.authenticate(username = username, password = password, projectName = projectName)) {
                is NetworkResult.Error -> {
                    _keystoneState.value = _keystoneState.value.copy(loginCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _keystoneState.value = _keystoneState.value.copy(loginCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success<*> -> {
                    _keystoneState.value = _keystoneState.value.copy(loginCallStatus = ApiCallStatus.SUCCESS)
                }
            }
        }
    }

    fun retrieveProjects() {
        _keystoneState.value = _keystoneState.value.copy(projectListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.getAllProjects()) {
                is NetworkResult.Error -> {
                    _keystoneState.value = _keystoneState.value.copy(projectListCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _keystoneState.value = _keystoneState.value.copy(projectListCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success -> {
                    val listOfRetrievedProjects = response.data.projects?.map { project ->
                        Project(id = project.id, name = project.name)
                    }?.toList()

                    println(listOfRetrievedProjects?.first()?.name)
                    listOfRetrievedProjects?.first()?.name?.let { projectName ->
                        val projectId = listOfRetrievedProjects.find {
                            it.name == projectName
                        }?.id
                        projectId?.let {
                            RepositoriesHolder.sharedPreferencesRepository.setSelectedProjectIdValue(projectId)
                        }
                    }
                    _keystoneState.value = _keystoneState.value.copy(projectListCallStatus = ApiCallStatus.SUCCESS, listOfRetrievedProjects = listOfRetrievedProjects)
                }
            }
        }
    }

    fun saveSelectedProjectId(projectName: String) {
        val projectId = _keystoneState.value.listOfRetrievedProjects?.find {
            it.name == projectName
        }?.id

        projectId?.let {
            RepositoriesHolder.sharedPreferencesRepository.setSelectedProjectIdValue(projectId)
        }
    }
}