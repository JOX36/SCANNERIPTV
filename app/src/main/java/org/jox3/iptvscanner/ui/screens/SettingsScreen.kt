package org.jox3.iptvscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jox3.iptvscanner.MainViewModel
import org.jox3.iptvscanner.data.AuthState
import org.jox3.iptvscanner.data.DataStoreManager

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dataStore = remember { DataStoreManager(context) }
    val scope = rememberCoroutineScope()

    var apiId by remember { mutableStateOf("") }
    var apiHash by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var botToken by remember { mutableStateOf("") }
    var chatId by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    // Cargar credenciales guardadas
    LaunchedEffect(Unit) {
        dataStore.getCredentials().collect { creds ->
            apiId = creds.apiId
            apiHash = creds.apiHash
            phone = creds.phone
            botToken = creds.botToken
            chatId = creds.chatId
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "⚙️ CONFIGURACIÓN",
            fontSize = 22.sp,
            color = Color(0xFFFF00FF)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // === Sección de credenciales de Telegram ===
        Text("Credenciales de Telegram", color = Color(0xFF00FFCC))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono (+57...)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                scope.launch {
                    dataStore.saveCredentials(apiId, apiHash, phone, botToken, chatId)
                    viewModel.sendPhoneNumber(phone)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFCC))
        ) {
            Text("Iniciar sesión en Telegram", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Estado de autenticación
        when (authState) {
            is AuthState.LoggedIn -> {
                Text("✅ Sesión iniciada correctamente", color = Color(0xFF00FF88))
            }
            is AuthState.Error -> {
                Text("❌ Error: ${(authState as AuthState.Error).message}", color = Color.Red)
            }
            else -> {
                Text("Estado: ${authState::class.simpleName}", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // === Guardar credenciales ===
        Button(
            onClick = {
                scope.launch {
                    dataStore.saveCredentials(apiId, apiHash, phone, botToken, chatId)
                    onNavigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88))
        ) {
            Text("💾 GUARDAR CREDENCIALES", color = Color.Black)
        }

        OutlinedButton(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⬅️ VOLVER AL INICIO")
        }
    }
}
