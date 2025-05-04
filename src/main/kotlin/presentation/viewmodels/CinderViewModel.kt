package presentation.viewmodels

import data.local.holder.RepositoriesHolder
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.models.Image
import domain.models.Volume
import domain.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.states.ApiCallStatus
import presentation.states.CinderPageState
import presentation.states.CreateNewVolumeOption

class CinderViewModel: ViewModel() {

    private val _cinderPageState: MutableStateFlow<CinderPageState> = MutableStateFlow(CinderPageState())
    val cinderPageState: StateFlow<CinderPageState> = _cinderPageState

    fun setCreatingVolumeType(creatingVolumeOption: CreateNewVolumeOption) {
        _cinderPageState.value = _cinderPageState.value.copy(
            createVolumeFromExistingOrNew = creatingVolumeOption
        )
    }

    fun setShowToast(show: Boolean) {
        _cinderPageState.value = _cinderPageState.value.copy(showToast = show)
    }

    fun setShowLoading(showLoading: Boolean) {
        _cinderPageState.value = _cinderPageState.value.copy(showLoading = showLoading)
    }

    fun setVolumesListCallStatus(apiCallStatus: ApiCallStatus) {
        _cinderPageState.value = _cinderPageState.value.copy(volumesListCallStatus = apiCallStatus)
    }

    fun setImagesListCallStatus(apiCallStatus: ApiCallStatus) {
        _cinderPageState.value = _cinderPageState.value.copy(imagesListCallStatus = apiCallStatus)
    }

    fun setVolumeNewCallStatus(apiCallStatus: ApiCallStatus) {
        _cinderPageState.value = _cinderPageState.value.copy(volumeNewCallStatus = apiCallStatus)
    }

    fun setVolumeExistedCallStatus(apiCallStatus: ApiCallStatus) {
        _cinderPageState.value = _cinderPageState.value.copy(volumeExistedCallStatus = apiCallStatus)
    }

    fun setDeleteVolumeCallStatus(apiCallStatus: ApiCallStatus) {
        _cinderPageState.value = _cinderPageState.value.copy(deleteVolumeCallStatus = apiCallStatus)
    }

    fun retrieveVolumes() {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        _cinderPageState.value = _cinderPageState.value.copy(volumesListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.getAllVolumes(projectId)) {
                is NetworkResult.Error -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumesListCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumesListCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success -> {
                    val listOfRetrievedVolumes = response.data.volumes?.map { volume ->
                        Volume(id = volume.id, name = volume.name)
                    }?.toList() ?: listOf()
                    listOfRetrievedVolumes.first().id?.let { id ->
                        RepositoriesHolder.sharedPreferencesRepository.setSelectedVolumeIdValue(id)
                    }
                    _cinderPageState.value = _cinderPageState.value.copy(volumesListCallStatus = ApiCallStatus.SUCCESS, listOfRetrievedVolumes = listOfRetrievedVolumes)
                }
            }
        }
    }

    fun deleteVolume(volumeId: String) {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        _cinderPageState.value = _cinderPageState.value.copy(deleteVolumeCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.deleteVolume(projectId, volumeId)) {
                is NetworkResult.Error -> {
                    _cinderPageState.value = _cinderPageState.value.copy(deleteVolumeCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _cinderPageState.value = _cinderPageState.value.copy(deleteVolumeCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success -> {
                    _cinderPageState.value = _cinderPageState.value.copy(deleteVolumeCallStatus = ApiCallStatus.SUCCESS, errorMessage = "Successfully deleted volume with id\n$volumeId")
                }
            }
        }
    }

    fun retrieveImages() {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        _cinderPageState.value = _cinderPageState.value.copy(imagesListCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.getAllImages()) {
                is NetworkResult.Error -> {
                    _cinderPageState.value = _cinderPageState.value.copy(imagesListCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _cinderPageState.value = _cinderPageState.value.copy(imagesListCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success -> {
                    val listOfRetrievedImages = response.data.images?.map { image ->
                        Image(id = image.id, name = image.name)
                    }?.toList() ?: listOf()
                    listOfRetrievedImages.first().id?.let { id ->
                        RepositoriesHolder.sharedPreferencesRepository.setSelectedImageIdValue(id)
                    }
                    _cinderPageState.value = _cinderPageState.value.copy(imagesListCallStatus = ApiCallStatus.SUCCESS, listOfRetrievedImages = listOfRetrievedImages)
                }
            }
        }
    }

    fun setSelectedVolume(id: String, name: String) {
        RepositoriesHolder.sharedPreferencesRepository.setSelectedVolumeIdValue(id)
        _cinderPageState.value = _cinderPageState.value.copy(selectedVolumeName = name, selectedVolumeId = id)
    }

    fun setSelectedImage(id: String, name: String) {
        RepositoriesHolder.sharedPreferencesRepository.setSelectedImageIdValue(id)
        _cinderPageState.value = _cinderPageState.value.copy(selectedImageId = id, selectedImageName = name)
    }

    fun creatVolumeFromSelectedOne(name: String, size: Int, description: String) {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        val volumeId = RepositoriesHolder.sharedPreferencesRepository.getSelectedVolumeIdValue()
        _cinderPageState.value = _cinderPageState.value.copy(volumeExistedCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.createVolumeFromExistingOne(projectId = projectId, name = name, size = size, description = description, existingVolumeId = volumeId)) {
                is NetworkResult.Error -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumeExistedCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumeExistedCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumeExistedCallStatus = ApiCallStatus.SUCCESS, errorMessage = "Success creating new volume")
                }
            }
        }

    }

    fun createNewVolume(name: String, size: Int, description: String) {
        val projectId = RepositoriesHolder.sharedPreferencesRepository.getSelectedProjectIdValue()
        _cinderPageState.value = _cinderPageState.value.copy(volumeNewCallStatus = ApiCallStatus.CALLING)
        CoroutineScope(Dispatchers.IO).launch{
            when(val response = RepositoriesHolder.openstackRepository.createVolume(projectId, name, size, description)) {
                is NetworkResult.Error -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumeNewCallStatus = ApiCallStatus.FAILED, errorCode = response.code, errorMessage = response.message)
                }
                is NetworkResult.Exception -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumeNewCallStatus = ApiCallStatus.FAILED, errorCode = -1, errorMessage = response.e.message)
                }
                is NetworkResult.Success -> {
                    _cinderPageState.value = _cinderPageState.value.copy(volumeNewCallStatus = ApiCallStatus.SUCCESS, errorMessage = "Success creating new volume")
                }
            }
        }
    }
}