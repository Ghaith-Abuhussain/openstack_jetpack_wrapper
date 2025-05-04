package presentation.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import presentation.states.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import presentation.states.LoginStatus

class MainViewModel: ViewModel() {

    private val _bottomNavigationBarSelectedItemState = MutableStateFlow<Navigation>(Navigation.KEYSTONE)
    val bottomNavigationBarSelectedItemState: StateFlow<Navigation> = _bottomNavigationBarSelectedItemState

    private val _loginState = MutableStateFlow(LoginStatus.LOGGED_OUT)
    val loginState: StateFlow<LoginStatus> = _loginState

    fun setBottomNavigationBarSelectedItem(navigation: Navigation) {
        _bottomNavigationBarSelectedItemState.value = navigation
    }

    fun setLoginStatusSelected(status: LoginStatus) {
        _loginState.value = status
    }

    fun logout() {
        _bottomNavigationBarSelectedItemState.value = Navigation.KEYSTONE
        _loginState.value = LoginStatus.LOGGED_OUT
    }
}