package org.jox3.iptvscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jox3.iptvscanner.data.TDLibManager
import org.jox3.iptvscanner.data.models.IptvResult

class MainViewModel : ViewModel() {

    private val tdLibManager = TDLibManager()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _logText = MutableStateFlow("Listo para escanear de verdad...\n")
    val logText: StateFlow<String> = _logText

    private val _results = MutableStateFlow<List<IptvResult>>(emptyList())
    val results: StateFlow<List<IptvResult>> = _results

    val authState = tdLibManager.authState

    init {
        tdLibManager.initialize()
    }

    fun addLog(message: String) {
        _logText.value += "$message\n"
    }

    fun updateProgress(value: Float) {
        _progress.value = value
    }

    fun startRealScan() {
        viewModelScope.launch {
            addLog("🚀 Iniciando escaneo REAL con TDLib...")

            if (!tdLibManager.isLoggedIn()) {
                addLog("❌ No estás logueado en Telegram")
                return@launch
            }

            // Aquí irá la lógica de escaneo real
            addLog("✅ Escaneo iniciado (pendiente de completar)")
        }
    }

    fun sendResultsToTelegram() {
        addLog("📤 Enviando resultados a Telegram...")
    }

    // Funciones de login (las usaremos más adelante)
    fun sendPhoneNumber(phone: String) {
        viewModelScope.launch {
            tdLibManager.sendPhoneNumber(phone)
        }
    }

    fun sendCode(code: String) {
        viewModelScope.launch {
            tdLibManager.sendCode(code)
        }
    }
}
