package org.jox3.iptvscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jox3.iptvscanner.data.models.IptvResult

class MainViewModel : ViewModel() {

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _logText = MutableStateFlow("Listo para escanear...\n")
    val logText: StateFlow<String> = _logText

    private val _results = MutableStateFlow<List<IptvResult>>(emptyList())
    val results: StateFlow<List<IptvResult>> = _results

    fun addLog(message: String) {
        _logText.value += "$message\n"
    }

    fun updateProgress(value: Float) {
        _progress.value = value
    }

    fun startRealScan() {
        viewModelScope.launch {
            addLog("🚀 Iniciando escaneo REAL...")
            // Aquí conectaremos con Telegram más adelante
        }
    }

    fun sendResultsToTelegram() {
        addLog("📤 Enviando resultados a Telegram...")
        // Lógica de envío
    }
}
