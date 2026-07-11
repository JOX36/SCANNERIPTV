package org.jox3.iptvscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jox3.iptvscanner.data.TDLibManager
import org.jox3.iptvscanner.data.models.IptvResult

class MainViewModel : ViewModel() {

    // === TDLib Manager ===
    private val tdLibManager = TDLibManager()

    // === Estados observables ===
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _logText = MutableStateFlow("Listo para escanear de verdad...\n")
    val logText: StateFlow<String> = _logText

    private val _results = MutableStateFlow<List<IptvResult>>(emptyList())
    val results: StateFlow<List<IptvResult>> = _results

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    // Exponemos el estado de autenticación de TDLib
    val authState = tdLibManager.authState

    init {
        tdLibManager.initialize()
    }

    // === Funciones de Log y Progreso ===
    fun addLog(message: String) {
        _logText.value += "$message\n"
    }

    fun updateProgress(value: Float) {
        _progress.value = value.coerceIn(0f, 100f)
    }

    fun clearLog() {
        _logText.value = "Listo para escanear de verdad...\n"
    }

    // === Funciones de Telegram ===
    fun sendPhoneNumber(phone: String) {
        viewModelScope.launch {
            addLog("📱 Enviando número de teléfono...")
            tdLibManager.sendPhoneNumber(phone)
        }
    }

    fun sendCode(code: String) {
        viewModelScope.launch {
            addLog("🔑 Enviando código de verificación...")
            tdLibManager.sendCode(code)
        }
    }

    // === Funciones principales de la app ===
    fun startRealScan() {
        viewModelScope.launch {
            if (!tdLibManager.isLoggedIn()) {
                addLog("❌ No estás logueado en Telegram. Inicia sesión primero.")
                return@launch
            }

            _isScanning.value = true
            addLog("🚀 Iniciando escaneo REAL con TDLib...")
            updateProgress(0f)

            // TODO: Aquí irá la lógica real de escaneo
            // Ejemplo de flujo futuro:
            // val chats = tdLibManager.getChats()
            // val messages = tdLibManager.getChatMessages(chatId, limit)
            // val urls = extractM3uUrls(messages)
            // urls.forEach { url -> validateAndAdd(url) }

            addLog("✅ Escaneo completado (lógica real pendiente de implementar)")
            updateProgress(100f)
            _isScanning.value = false
        }
    }

    fun sendResultsToTelegram() {
        viewModelScope.launch {
            if (_results.value.isEmpty()) {
                addLog("⚠️ No hay resultados para enviar.")
                return@launch
            }

            addLog("📤 Enviando resultados a Telegram...")
            // TODO: Implementar envío real usando Ktor + Bot API
            addLog("✅ Resultados enviados (pendiente de implementar)")
        }
    }

    fun addResult(result: IptvResult) {
        _results.value = _results.value + result
    }

    fun clearResults() {
        _results.value = emptyList()
    }
}
