package org.jox3.iptvscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jox3.iptvscanner.MainViewModel
import org.jox3.iptvscanner.data.AuthState

@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    onLoginSuccess: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar sesión en Telegram",
            fontSize = 22.sp,
            color = Color(0xFF00FFCC)
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (authState) {
            is AuthState.Idle, is AuthState.WaitingForPhone -> {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Número de teléfono (+57...)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.sendPhoneNumber(phone) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enviar número")
                }
            }

            is AuthState.WaitingForCode -> {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Código de verificación") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.sendCode(code) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verificar código")
                }
            }

            is AuthState.WaitingForPassword -> {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña (2FA)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* viewModel.sendPassword(password) */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enviar contraseña")
                }
            }

            is AuthState.LoggedIn -> {
                Text("✅ Sesión iniciada correctamente", color = Color(0xFF00FF88))
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onLoginSuccess) {
                    Text("Continuar")
                }
            }

            is AuthState.Error -> {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Red
                )
            }
        }
    }
}
