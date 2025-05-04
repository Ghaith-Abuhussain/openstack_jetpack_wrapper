package presentation.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(onDismissRequest: () -> Unit = {}) {
    Dialog(onCloseRequest = onDismissRequest, undecorated = true) {
        Box(
            modifier = Modifier
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
