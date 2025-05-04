import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.local.holder.RepositoriesHolder
import data.remote.retrofit.builder.OpenStackRetrofitBuilder
import presentation.app.App
import presentation.viewmodels.MainViewModel

fun main() = application {
    RepositoriesHolder.build()
    OpenStackRetrofitBuilder.build()
    val mainViewModel = rememberSaveable { MainViewModel() }

    Window(onCloseRequest = ::exitApplication) {
        App(mainViewModel)
    }
}
