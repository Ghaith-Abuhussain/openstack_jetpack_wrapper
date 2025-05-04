package presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.components.LoadingDialog
import presentation.components.RadioGroup
import presentation.components.ToastHost
import presentation.components.IdNameCard
import presentation.states.ApiCallStatus
import presentation.states.CreateNewVolumeOption
import presentation.viewmodels.CinderViewModel

@Composable
fun CinderScreen(cinderViewModel: CinderViewModel, onLogout: () -> Unit) {
    val cinderPageState = cinderViewModel.cinderPageState.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    var size by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    when (cinderPageState.value.volumesListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            cinderViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.retrieveImages()
            cinderViewModel.setVolumesListCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.setVolumesListCallStatus(ApiCallStatus.INITIALIZE)
            if(cinderPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (cinderPageState.value.imagesListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            cinderViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setImagesListCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.setImagesListCallStatus(ApiCallStatus.INITIALIZE)
            if(cinderPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when(cinderPageState.value.volumeNewCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            cinderViewModel.setShowLoading(true)
        }
        ApiCallStatus.SUCCESS -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.retrieveVolumes()
            cinderViewModel.setVolumeNewCallStatus(ApiCallStatus.INITIALIZE)
        }
        ApiCallStatus.FAILED -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.setVolumeNewCallStatus(ApiCallStatus.INITIALIZE)
            if(cinderPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when(cinderPageState.value.volumeExistedCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            cinderViewModel.setShowLoading(true)
        }
        ApiCallStatus.SUCCESS -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.retrieveVolumes()
            cinderViewModel.setVolumeExistedCallStatus(ApiCallStatus.INITIALIZE)
        }
        ApiCallStatus.FAILED -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.setVolumeExistedCallStatus(ApiCallStatus.INITIALIZE)
            if(cinderPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when(cinderPageState.value.deleteVolumeCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            cinderViewModel.setShowLoading(true)
        }
        ApiCallStatus.SUCCESS -> {
            rememberCoroutineScope().launch {
                delay(3000)
                cinderViewModel.setShowLoading(false)
                cinderViewModel.setShowToast(true)
                cinderViewModel.retrieveVolumes()
                cinderViewModel.setDeleteVolumeCallStatus(ApiCallStatus.INITIALIZE)
            }
        }
        ApiCallStatus.FAILED -> {
            cinderViewModel.setShowLoading(false)
            cinderViewModel.setShowToast(true)
            cinderViewModel.setDeleteVolumeCallStatus(ApiCallStatus.INITIALIZE)
            if(cinderPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    LaunchedEffect(true) {
        cinderViewModel.retrieveVolumes()
    }

    LaunchedEffect(cinderPageState.value.showToast) {
        if (cinderPageState.value.showToast) {
            delay(2000)
            cinderViewModel.setShowToast(false)
        }
    }

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Create Volume", style = MaterialTheme.typography.h3)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = size,
                    onValueChange = { size = it },
                    label = { Text("Size in GB") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                RadioGroup(
                    title = "Select an option:",
                    CreateNewVolumeOption.entries.map { it.name }.toList(),
                    onOptionSelected = { selectedText ->
                        if (selectedText == CreateNewVolumeOption.NEW_VOLUME.name) {
                            cinderViewModel.setCreatingVolumeType(CreateNewVolumeOption.NEW_VOLUME)
                        } else {
                            cinderViewModel.setCreatingVolumeType(CreateNewVolumeOption.EXISTING_VOLUME)
                        }
                    },
                )

                Button(
                    onClick = {
                        val size = try {
                            size.toInt()
                        } catch (e: Exception) {
                            1
                        }
                        if (cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.NEW_VOLUME) {
                            cinderViewModel.createNewVolume(name, size, description)
                        } else {
                            cinderViewModel.creatVolumeFromSelectedOne(name, size, description)
                        }
                    },
                    enabled = if (cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.NEW_VOLUME) if(cinderPageState.value.selectedImageId.isNullOrEmpty()) false else true else if (cinderPageState.value.selectedVolumeId.isNullOrEmpty()) false else true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create")
                }
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) "Select Volume" else "Select Image", style = MaterialTheme.typography.h3)
                Column {
                    Text(if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) "Selected volume: " else "Selected image: ")
                    IdNameCard(
                        modifier = Modifier.fillMaxWidth(),
                        name = if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) cinderPageState.value.selectedVolumeName else cinderPageState.value.selectedImageName,
                        id = if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) cinderPageState.value.selectedVolumeId else cinderPageState.value.selectedImageId,
                        backgroundColor = Color.Green.copy(alpha = 0.5f),
                        onClick = {_,_ ->},
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) cinderPageState.value.listOfRetrievedVolumes.size else cinderPageState.value.listOfRetrievedImages.size) { index ->
                        IdNameCard(
                            modifier = Modifier.fillParentMaxWidth(),
                            name = if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) cinderPageState.value.listOfRetrievedVolumes[index].name else cinderPageState.value.listOfRetrievedImages[index].name,
                            id = if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) cinderPageState.value.listOfRetrievedVolumes[index].id else cinderPageState.value.listOfRetrievedImages[index].id,
                            onClick = { id, name ->
                                if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) {
                                    cinderViewModel.setSelectedVolume(id, name)
                                } else {
                                    cinderViewModel.setSelectedImage(id, name)
                                }
                            },
                            showDeleteButton = cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME,
                            onDelete = {
                                if(cinderPageState.value.createVolumeFromExistingOrNew == CreateNewVolumeOption.EXISTING_VOLUME) {
                                    cinderPageState.value.listOfRetrievedVolumes[index].id?.let { id ->
                                        cinderViewModel.deleteVolume(id)
                                    }
                                }
                            }
                        )
                    }
                }
            }

        }
    }

    if (cinderPageState.value.showLoading) {
        LoadingDialog {
        }
    }
    ToastHost(
        showToast = cinderPageState.value.showToast,
        message = cinderPageState.value.errorMessage ?: "Unknown error"
    )
}