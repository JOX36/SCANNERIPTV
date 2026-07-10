package org.jox3.iptvscanner.data

import org.jox3.iptvscanner.data.models.IptvResult

class TelegramRepository {

    suspend fun scanChatForM3uLinks(chatId: Long): List<String> {
        // Aquí irá la lógica real con TDLib o Bot API
        return emptyList()
    }

    suspend fun validateM3uUrl(url: String): IptvResult? {
        // Validación real (Ktor + detección LATINA)
        return null
    }

    suspend fun sendResultsToTelegram(results: List<IptvResult>, botToken: String, chatId: String) {
        // Envío real usando Bot API
    }
}
