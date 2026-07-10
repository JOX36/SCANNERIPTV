package org.jox3.iptvscanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(onNavigateToSettings: () -> Unit) {
    var progress by remember { mutableStateOf(0f) }
    var logText by remember { mutableStateOf("Listo para escanear...\n") }
    var statusText by remember { mutableStateOf("✅ Credenciales cargadas") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "🌌 IPTV SUPER SCANNER v4.5",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00FFCC),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(text = statusText, color = Color(0xFF00FFCC))

        Spacer(modifier = Modifier.height(12.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = { progress / 100f },
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF00FFCC)
        )
        Text(
            text = "Progreso: ${progress.toInt()}%",
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Log
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0033))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
            ) {
                Text(
                    text = logText,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Button(
            onClick = {
                logText += "🚀 Iniciando escaneo REAL...\n"
                // Aquí irá la lógica real más adelante
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFCC))
        ) {
            Text("🌍 ESCANEAR + VALIDAR (REAL)", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = { /* Enviar a Telegram */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88))
        ) {
            Text("📤 ENVIAR RESULTADOS A TELEGRAM", color = Color.Black)
        }

        OutlinedButton(
            onClick = onNavigateToSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⚙️ CONFIGURACIÓN")
        }
    }
}
