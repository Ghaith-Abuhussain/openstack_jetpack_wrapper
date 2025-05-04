package presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import presentation.states.LoginStatus
import presentation.viewmodels.KeystoneViewModel
import presentation.viewmodels.MainViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import presentation.components.DropdownMenu
import presentation.components.LoadingDialog
import presentation.components.ToastHost
import presentation.states.ApiCallStatus

@Composable
fun KeystoneScreen(keyStoneViewModel: KeystoneViewModel, mainViewModel: MainViewModel, onLogout: () -> Unit) {

    val keyStoneState = keyStoneViewModel.keystoneState.collectAsState()
    val loginState = mainViewModel.loginState.collectAsState()
    when (keyStoneState.value.loginCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            //Show loading dialog
            keyStoneViewModel.setShowLoading(true)
        }
        ApiCallStatus.SUCCESS -> {
            keyStoneViewModel.setShowLoading(false)
            mainViewModel.setLoginStatusSelected(LoginStatus.LOGGED_IN)
            keyStoneViewModel.retrieveProjects()
            keyStoneViewModel.setLoginApiCallingState(ApiCallStatus.INITIALIZE)
        }
        ApiCallStatus.FAILED -> {
            // Show Toast of failure
            keyStoneViewModel.setShowLoading(false)
            keyStoneViewModel.setShowToast(true)
            keyStoneViewModel.setLoginApiCallingState(ApiCallStatus.INITIALIZE)
            if(keyStoneState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    when (keyStoneState.value.projectListCallStatus) {
        ApiCallStatus.INITIALIZE -> {}
        ApiCallStatus.CALLING -> {
            keyStoneViewModel.setShowLoading(true)
        }
        ApiCallStatus.SUCCESS -> {
            keyStoneViewModel.setShowLoading(false)
        }
        ApiCallStatus.FAILED -> {
            keyStoneViewModel.setShowLoading(false)
            keyStoneViewModel.setShowToast(true)
            keyStoneViewModel.setProjectListApiCallingState(ApiCallStatus.INITIALIZE)
            if(keyStoneState.value.errorCode == 401) {
                onLogout()
            }
        }
    }

    LaunchedEffect(keyStoneState.value.showToast) {
        if (keyStoneState.value.showToast) {
            delay(2000)
            keyStoneViewModel.setShowToast(false)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (loginState.value == LoginStatus.LOGGED_OUT) {
            var projectName by rememberSaveable { mutableStateOf("") }
            var username by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            Column(
                modifier = Modifier.fillMaxSize(0.7f)
                    .padding(all = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Please login to continue")
                OutlinedTextField(
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text("Project Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        println("Logging in with $username / $password")
                        keyStoneViewModel.login(username, password, projectName)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        } else {
            if(!keyStoneState.value.listOfRetrievedProjects.isNullOrEmpty()) {
                Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    DropdownMenu(keyStoneState.value.listOfRetrievedProjects?.map {
                        it.name ?: ""
                    }?.toList() ?: listOf(), onSelected = { projectName ->
                        keyStoneViewModel.saveSelectedProjectId(projectName)
                    })
                    Button(
                        onClick = {
                            mainViewModel.setLoginStatusSelected(LoginStatus.LOGGED_OUT)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Logout")
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error retrieve project list")
                    Button(
                        onClick = {
                            keyStoneViewModel.retrieveProjects()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Retrieve Projects")
                    }

                    Button(
                        onClick = {
                            mainViewModel.setLoginStatusSelected(LoginStatus.LOGGED_OUT)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Logout")
                    }
                }
            }
        }

        if (keyStoneState.value.showLoading) {
            LoadingDialog {
            }
        }
        ToastHost(showToast = keyStoneState.value.showToast, message = keyStoneState.value.errorMessage ?: "Unknown error")
    }

}