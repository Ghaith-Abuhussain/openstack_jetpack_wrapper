package presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.Network
import domain.models.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.components.IdNameCard
import presentation.components.LoadingDialog
import presentation.components.RadioGroup
import presentation.components.ToastHost
import presentation.states.ApiCallStatus
import presentation.viewmodels.NeutronViewModel

@Composable
fun NeutronScreen(neutronViewModel: NeutronViewModel, onLogout: () -> Unit) {

    val neutronPageState = neutronViewModel.neutronPageState.collectAsState()
    var routerName by rememberSaveable { mutableStateOf("") }
    var routerAdminStateUp by rememberSaveable { mutableStateOf(true) }

    var networkName by rememberSaveable { mutableStateOf("") }
    var networkAdminStateUp by rememberSaveable { mutableStateOf(true) }
    var networkIsExternal by rememberSaveable { mutableStateOf(false) }

    var subnetName by rememberSaveable { mutableStateOf("") }
    var subNetIpPool by rememberSaveable { mutableStateOf("") }
    var subnetDnsServer by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()

    when (neutronPageState.value.routerListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.retrieveNetworks()
            neutronViewModel.setRouterListCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setRouterListCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (neutronPageState.value.networkListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setNetworkListCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setNetworkListCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (neutronPageState.value.createRouterCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.retrieveRouters()
            neutronViewModel.setCreateRouterCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setCreateRouterCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (neutronPageState.value.deleteRouterCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            rememberCoroutineScope().launch {
                delay(3000)
                neutronViewModel.setShowLoading(false)
                neutronViewModel.setShowToast(true)
                neutronViewModel.retrieveRouters()
                neutronViewModel.setDeleteRouterCallStatus(ApiCallStatus.INITIALIZE)
            }
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setDeleteRouterCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (neutronPageState.value.createNetworkCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.retrieveNetworks()
            neutronViewModel.setCreateNetworkCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setCreateNetworkCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (neutronPageState.value.deleteNetworkCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            rememberCoroutineScope().launch {
                delay(3000)
                neutronViewModel.setShowLoading(false)
                neutronViewModel.setShowToast(true)
                neutronViewModel.retrieveNetworks()
                neutronViewModel.setDeleteNetworkCallStatus(ApiCallStatus.INITIALIZE)
            }
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setDeleteNetworkCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (neutronPageState.value.createSubnetCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            neutronViewModel.setShowLoading(true)
        }

        ApiCallStatus.SUCCESS -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setCreateSubnetCallStatus(ApiCallStatus.INITIALIZE)
        }

        ApiCallStatus.FAILED -> {
            neutronViewModel.setShowLoading(false)
            neutronViewModel.setShowToast(true)
            neutronViewModel.setCreateSubnetCallStatus(ApiCallStatus.INITIALIZE)
            if(neutronPageState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    LaunchedEffect(true) {
        neutronViewModel.retrieveRouters()
    }

    LaunchedEffect(neutronPageState.value.showToast) {
        if (neutronPageState.value.showToast) {
            delay(2000)
            neutronViewModel.setShowToast(false)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = rememberLazyListState()
    ) {
        item {
            Text(text = "Routers", style = MaterialTheme.typography.h2)
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
                        Text("Create Router", style = MaterialTheme.typography.h3)
                        OutlinedTextField(
                            value = routerName,
                            onValueChange = { routerName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        RadioGroup(
                            title = "Admin State Up:",
                            listOf("True", "False"),
                            onOptionSelected = { selectedText ->
                                if (selectedText == "True") {
                                    routerAdminStateUp = true
                                } else {
                                    routerAdminStateUp = false
                                }
                            },
                        )

                        Button(
                            onClick = {
                                neutronViewModel.createRouter(name = routerName, adminStateUp = routerAdminStateUp)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Create Router")
                        }
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Select Router", style = MaterialTheme.typography.h3)
                        Column {
                            Text("Selected Router: ")
                            IdNameCard(
                                modifier = Modifier.fillMaxWidth(),
                                name = neutronPageState.value.selectedRouter.name,
                                id = neutronPageState.value.selectedRouter.id,
                                backgroundColor = Color.Green.copy(alpha = 0.5f),
                                onClick = { _, _ -> },
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            items(neutronPageState.value.listOfRetrievedRouters.size) { index ->
                                IdNameCard(
                                    modifier = Modifier.fillParentMaxWidth(),
                                    name = neutronPageState.value.listOfRetrievedRouters[index].name,
                                    id = neutronPageState.value.listOfRetrievedRouters[index].id,
                                    onClick = { id, name ->
                                        neutronViewModel.setSelectedRouter(Router(id = id, name = name))
                                    },
                                    showDeleteButton = true,
                                    onDelete = {
                                        neutronPageState.value.listOfRetrievedRouters[index].id?.let { id ->
                                            neutronViewModel.deleteRouter(id)
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
            Text(text = "Networks", style = MaterialTheme.typography.h2)
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
                        Text("Create Network", style = MaterialTheme.typography.h3)
                        OutlinedTextField(
                            value = networkName,
                            onValueChange = { networkName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        RadioGroup(
                            title = "Admin State Up:",
                            listOf("True", "False"),
                            onOptionSelected = { selectedText ->
                                if (selectedText == "True") {
                                    networkAdminStateUp = true
                                } else {
                                    networkAdminStateUp = false
                                }
                            },
                        )

                        RadioGroup(
                            title = "Is network external:",
                            listOf("True", "False"),
                            onOptionSelected = { selectedText ->
                                if (selectedText == "True") {
                                    networkIsExternal = true
                                } else {
                                    networkIsExternal = false
                                }
                            },
                        )

                        Button(
                            onClick = {
                                neutronViewModel.createNetwork(name = networkName, adminStateUp = networkAdminStateUp, isExternal = networkIsExternal)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Create Network")
                        }
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Select Network", style = MaterialTheme.typography.h3)
                        Column {
                            Text("Selected Network: ")
                            IdNameCard(
                                modifier = Modifier.fillMaxWidth(),
                                name = neutronPageState.value.selectedNetwork.name,
                                id = neutronPageState.value.selectedNetwork.id,
                                backgroundColor = Color.Green.copy(alpha = 0.5f),
                                onClick = { _, _ -> },
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            items(neutronPageState.value.listOfRetrievedNetworks.size) { index ->
                                IdNameCard(
                                    modifier = Modifier.fillParentMaxWidth(),
                                    name = neutronPageState.value.listOfRetrievedNetworks[index].name,
                                    id = neutronPageState.value.listOfRetrievedNetworks[index].id,
                                    onClick = { id, name ->
                                        neutronViewModel.setSelectedNetwork(Network(id = id, name = name))
                                    },
                                    showDeleteButton = true,
                                    onDelete = {
                                        neutronPageState.value.listOfRetrievedNetworks[index].id?.let { id ->
                                            neutronViewModel.deleteNetwork(id)
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
            Text(text = "Subnets", style = MaterialTheme.typography.h2)
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
                        Text("Create Subnet and Add To Router", style = MaterialTheme.typography.h3)
                        OutlinedTextField(
                            value = subnetName,
                            onValueChange = { subnetName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = subNetIpPool,
                            onValueChange = { subNetIpPool = it },
                            label = { Text("IP pool x.x.x.x/xx") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = subnetDnsServer,
                            onValueChange = { subnetDnsServer = it },
                            label = { Text("DNS server: x.x.x.x") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                neutronViewModel.createSubnet(
                                    ipPool = subNetIpPool,
                                    name = subnetName,
                                    dnsServer = subnetDnsServer
                                )
                            },
                            enabled = if (neutronPageState.value.selectedRouter.id.isNullOrEmpty() || neutronPageState.value.selectedNetwork.id.isNullOrEmpty()) false else true,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Create Subnet")
                        }
                    }
                }
            }
        }
    }
    if (neutronPageState.value.showLoading) {
        LoadingDialog {
        }
    }
    ToastHost(
        showToast = neutronPageState.value.showToast,
        message = neutronPageState.value.errorMessage ?: "Unknown error"
    )
}