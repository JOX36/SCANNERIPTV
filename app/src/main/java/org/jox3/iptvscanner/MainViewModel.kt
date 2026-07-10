package org.jox3.iptvscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jox3.iptvscanner.data.TelegramRepository
import org.jox3.iptvscanner.data.models.IptvResult

class MainViewModel : ViewModel() {

    private val repository = TelegramRepository()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _logText = MutableStateFlow("Listo para escanear de verdad...\n")
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
            addLog("🚀 Iniciando escaneo REAL con Telegram...")
            
            // Ejemplo de flujo (pendiente de implementar)
            // val urls = repository.scanChatForM3u(selectedChatId)
            // urls.forEachIndexed { index, url ->
            //     val result = repository.validateM3u(url)
            //     if (result != null) _results.value += result
            //     updateProgress((index + 1f) / urls.size * 100)
            // }
            
            addLog("✅ Escaneo completado (pendiente de implementación real)")
        }
    }

    fun sendResultsToTelegram() {
        viewModelScope.launch {
            addLog("📤 Enviando resultados a Telegram...")
            // repository.sendResultsToBot(_results.value, botToken, chatId)
            addLog("✅ Resultados enviados (pendiente de implementación)")
        }
    }
}
