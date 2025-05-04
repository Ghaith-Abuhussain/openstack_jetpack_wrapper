package presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun IdNameCard(modifier: Modifier = Modifier, backgroundColor: Color = Color.White, name: String?, id: String?, showDeleteButton: Boolean = false, onClick: (id: String, name: String) -> Unit, onDelete: () -> Unit = {}) {
    Card(
        modifier = modifier.clickable {
            id?.let { id ->
                name?.let { name ->
                    onClick(id, name)
                }
            }
        },
        shape = RoundedCornerShape(size = 8.dp),
        backgroundColor = backgroundColor,
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.Start) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Name: ", style = TextStyle(color = Color.Gray), modifier = Modifier.width(60.dp))
                Text(name ?: "", style = TextStyle(color = Color.Black), modifier = Modifier.fillMaxWidth())
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("ID: ", style = TextStyle(color = Color.Gray), modifier = Modifier.width(60.dp))
                Text(id ?: "", style = TextStyle(color = Color.Black), modifier = Modifier.fillMaxWidth())
            }
            if(showDeleteButton) {
                Button(
                    onClick = { onDelete() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                ) {
                    Text("Delete", color = MaterialTheme.colors.onError)
                }
            }
        }
    }
}