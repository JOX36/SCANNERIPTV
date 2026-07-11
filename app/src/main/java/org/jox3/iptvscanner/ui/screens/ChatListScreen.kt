package org.jox3.iptvscanner.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatListScreen(
    chats: List<ChatInfo>,
    onChatSelected: (Long) -> Unit
) {
    LazyColumn {
        items(chats) { chat ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onChatSelected(chat.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(chat.title, style = MaterialTheme.typography.titleMedium)
                    Text(chat.type, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

data class ChatInfo(
    val id: Long,
    val title: String,
    val type: String
)
