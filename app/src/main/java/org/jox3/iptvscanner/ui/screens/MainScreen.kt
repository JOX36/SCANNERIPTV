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
import org.jox3.iptvscanner.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onNavigateToSettings: () -> Unit
) {
    val progress by viewModel.progress.collectAsState()
    val logText by viewModel.logText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🌌 IPTV SUPER SCANNER v4.5 (Kotlin)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00FFCC)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress / 100 },
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF00FFCC)
        )
        Text("${progress.toInt()}%", color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

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
                Text(logText, color = Color.White, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.startRealScan() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFCC))
        ) {
            Text("🌍 ESCANEAR + VALIDAR (REAL)", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = { viewModel.sendResultsToTelegram() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88))
        ) {
            Text("📤 ENVIAR A TELEGRAM", color = Color.Black)
        }

        OutlinedButton(
            onClick = onNavigateToSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⚙️ CONFIGURACIÓN")
        }
    }
}
