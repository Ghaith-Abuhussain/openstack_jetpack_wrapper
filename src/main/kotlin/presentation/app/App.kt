package presentation.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import presentation.viewmodels.MainViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.screens.CinderScreen
import presentation.screens.KeystoneScreen
import presentation.screens.NeutronScreen
import presentation.screens.NovaScreen
import presentation.states.LoginStatus
import presentation.states.Navigation
import presentation.viewmodels.CinderViewModel
import presentation.viewmodels.KeystoneViewModel
import presentation.viewmodels.NeutronViewModel
import presentation.viewmodels.NovaViewModel

@Composable
@Preview
fun App(mainViewModel: MainViewModel) {
    MaterialTheme {
        val selectedItem = mainViewModel.bottomNavigationBarSelectedItemState.collectAsState()
        val loginState = mainViewModel.loginState.collectAsState()

        val keystoneViewModel = rememberSaveable { KeystoneViewModel() }
        val cinderViewModel = rememberSaveable { CinderViewModel() }
        val neutronViewModel = rememberSaveable { NeutronViewModel() }
        val novaViewModel = rememberSaveable { NovaViewModel() }

        Column(modifier = Modifier.fillMaxSize()) {
            // Content Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                when (selectedItem.value) {
                    Navigation.KEYSTONE -> {
                        KeystoneScreen(
                            keystoneViewModel, mainViewModel,
                            onLogout = {
                                mainViewModel.logout()
                            },
                        )
                    }

                    Navigation.CINDER -> CinderScreen(
                        cinderViewModel,
                        onLogout = {
                            mainViewModel.logout()
                        },
                    )

                    Navigation.NEUTRON -> NeutronScreen(
                        neutronViewModel,
                        onLogout = {
                            mainViewModel.logout()
                        },
                    )

                    Navigation.NOVA -> NovaScreen(
                        novaViewModel = novaViewModel,
                        neutronViewModel = neutronViewModel,
                        cinderViewModel = cinderViewModel, onLogout = {
                            mainViewModel.logout()
                        }
                    )
                }
            }

            // Bottom Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.DarkGray),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val items = Navigation.entries
                items.forEachIndexed { index, item ->
                    if (loginState.value == LoginStatus.LOGGED_IN) {
                        Text(
                            text = item.name,
                            color = if (selectedItem.value == Navigation.entries.toTypedArray()[index]) Color.White else Color.Gray,
                            modifier = Modifier
                                .clickable { mainViewModel.setBottomNavigationBarSelectedItem(Navigation.entries.toTypedArray()[index]) }
                                .padding(16.dp)
                        )
                    } else {
                        Text(
                            text = item.name,
                            color = if (selectedItem.value == Navigation.entries.toTypedArray()[index]) Color.White else Color.Gray,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}