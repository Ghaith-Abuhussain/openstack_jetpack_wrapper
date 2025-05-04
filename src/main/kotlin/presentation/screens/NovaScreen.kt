package presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import domain.models.Flavor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.components.IdNameCard
import presentation.components.LoadingDialog
import presentation.components.ToastHost
import presentation.states.ApiCallStatus
import presentation.viewmodels.CinderViewModel
import presentation.viewmodels.NeutronViewModel
import presentation.viewmodels.NovaViewModel

@Composable
fun NovaScreen(novaViewModel: NovaViewModel, cinderViewModel: CinderViewModel, neutronViewModel: NeutronViewModel, onLogout: () -> Unit) {

    val novaPageState = novaViewModel.novaPageState.collectAsState()
    val cinderPageState = cinderViewModel.cinderPageState.collectAsState()
    val neutronPageState = neutronViewModel.neutronPageState.collectAsState()

    var flavorName by rememberSaveable { mutableStateOf("") }
    var flavorRam by rememberSaveable { mutableStateOf("") }
    var flavorDisk by rememberSaveable { mutableStateOf("") }
    var vmName by rememberSaveable { mutableStateOf("") }

    when (novaPageState.value.flavorListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            novaViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.retrieveInstances()
            novaViewModel.setFlavorListCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.setFlavorListCallStatus(ApiCallStatus.INITIALIZE)
            if(novaPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (novaPageState.value.instanceListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            novaViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setInstanceListCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.setInstanceListCallStatus(ApiCallStatus.INITIALIZE)
            if(novaPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (novaPageState.value.createFlavorCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            novaViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.retrieveFlavors()
            novaViewModel.setCreateFlavorCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.setCreateFlavorCallStatus(ApiCallStatus.INITIALIZE)
            if(novaPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (novaPageState.value.deleteFlavorCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            novaViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            rememberCoroutineScope().launch {
                delay(3000)
                novaViewModel.setShowLoading(false)
                novaViewModel.setShowToast(true)
                novaViewModel.retrieveFlavors()
                novaViewModel.setDeleteFlavorCallStatus(ApiCallStatus.INITIALIZE)
            }
        }

        ApiCallStatus.FAILED -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.setDeleteFlavorCallStatus(ApiCallStatus.INITIALIZE)
            if(novaPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (novaPageState.value.deleteInstanceCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            novaViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            rememberCoroutineScope().launch {
                delay(3000)
                novaViewModel.setShowLoading(false)
                novaViewModel.setShowToast(true)
                novaViewModel.retrieveInstances()
                novaViewModel.setDeleteInstanceCallStatus(ApiCallStatus.INITIALIZE)
            }
        }

        ApiCallStatus.FAILED -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.setDeleteInstanceCallStatus(ApiCallStatus.INITIALIZE)
            if(novaPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (novaPageState.value.bootVMCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            novaViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.retrieveInstances()
            novaViewModel.setBootVMCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            novaViewModel.setShowLoading(false)
            novaViewModel.setShowToast(true)
            novaViewModel.setBootVMCallStatus(ApiCallStatus.INITIALIZE)
            if(novaPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    LaunchedEffect(true) {
        novaViewModel.retrieveFlavors()
    }

    LaunchedEffect(novaPageState.value.showToast) {
        if (novaPageState.value.showToast) {
            delay(2000)
            novaViewModel.setShowToast(false)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = rememberLazyListState()
    ) {
        item {
            Text(text = "Flavors", style = MaterialTheme.typography.h2)
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Create Flavor", style = MaterialTheme.typography.h3)
                        OutlinedTextField(
                            value = flavorName,
                            onValueChange = { flavorName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = flavorRam,
                            onValueChange = { flavorRam = it },
                            label = { Text("Ram in GB") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = flavorDisk,
                            onValueChange = { flavorDisk = it },
                            label = { Text("Disk in GB") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                val ram = try {
                                    flavorRam.toInt()
                                } catch (e: Exception) {
                                    0
                                }

                                val disk = try {
                                    flavorDisk.toInt()
                                } catch (e: Exception) {
                                    0
                                }
                                novaViewModel.createFlavor(name = flavorName, ram = ram, disk = disk)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Create Flavor")
                        }
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Select Flavor", style = MaterialTheme.typography.h3)
                        Column {
                            Text("Selected Flavor: ")
                            IdNameCard(
                                modifier = Modifier.fillMaxWidth(),
                                name = novaPageState.value.selectedFlavor.name,
                                id = novaPageState.value.selectedFlavor.id,
                                backgroundColor = Color.Green.copy(alpha = 0.5f),
                                onClick = { _, _ -> },
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            items(novaPageState.value.listOfRetrievedFlavors.size) { index ->
                                IdNameCard(
                                    modifier = Modifier.fillParentMaxWidth(),
                                    name = novaPageState.value.listOfRetrievedFlavors[index].name,
                                    id = novaPageState.value.listOfRetrievedFlavors[index].id,
                                    onClick = { id, name ->
                                        novaViewModel.setSelectedFlavor(Flavor(id = id, name = name))
                                    },
                                    showDeleteButton = true,
                                    onDelete = {
                                        novaPageState.value.listOfRetrievedFlavors[index].id?.let { id ->
                                            novaViewModel.deleteFlavor(id)
                                        }
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }

        item {
            Text(text = "VM", style = MaterialTheme.typography.h2)
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Boot Server", style = MaterialTheme.typography.h3)
                        OutlinedTextField(
                            value = vmName,
                            onValueChange = { vmName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                novaViewModel.bootVM(name = vmName)
                            },
                            enabled = if(novaPageState.value.selectedFlavor.id.isNullOrEmpty() || neutronPageState.value.selectedNetwork.id.isNullOrEmpty() || cinderPageState.value.selectedVolumeId.isNullOrEmpty()) false else true,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Boot Server")
                        }
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Servers", style = MaterialTheme.typography.h3)
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            items(novaPageState.value.listOfRetrievedServers.size) { index ->
                                IdNameCard(
                                    modifier = Modifier.fillParentMaxWidth(),
                                    name = novaPageState.value.listOfRetrievedServers[index].name,
                                    id = novaPageState.value.listOfRetrievedServers[index].id,
                                    onClick = { id, name ->
                                    },
                                    showDeleteButton = true,
                                    onDelete = {
                                        novaPageState.value.listOfRetrievedServers[index].id?.let { id ->
                                            novaViewModel.deleteInstance(id)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    if (novaPageState.value.showLoading) {
        LoadingDialog {
        }
    }
    ToastHost(
        showToast = novaPageState.value.showToast,
        message = novaPageState.value.errorMessage ?: "Unknown error"
    )
}