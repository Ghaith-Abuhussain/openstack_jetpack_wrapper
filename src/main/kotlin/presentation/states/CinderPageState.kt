package presentation.states

import domain.models.Image
import domain.models.Volume

data class CinderPageState(
    val errorCode: Int? = -1,
    val errorMessage: String? = "",
    val volumesListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val imagesListCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val volumeNewCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val volumeExistedCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val deleteVolumeCallStatus: ApiCallStatus = ApiCallStatus.INITIALIZE,
    val selectedVolumeId: String = "",
    val selectedVolumeName: String = "",
    val selectedImageId: String = "",
    val selectedImageName: String = "",
    val listOfRetrievedVolumes: List<Volume> = listOf(),
    val listOfRetrievedImages: List<Image> = listOf(),
    val createVolumeFromExistingOrNew: CreateNewVolumeOption = CreateNewVolumeOption.NEW_VOLUME,
    val showToast: Boolean = false,
    val showLoading: Boolean = false
)