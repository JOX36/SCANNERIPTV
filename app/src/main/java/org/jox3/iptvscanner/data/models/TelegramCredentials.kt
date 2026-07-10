package org.jox3.iptvscanner.data.models

data class TelegramCredentials(
    val apiId: String = "",
    val apiHash: String = "",
    val phone: String = "",
    val botToken: String = "",
    val chatId: String = ""
)
