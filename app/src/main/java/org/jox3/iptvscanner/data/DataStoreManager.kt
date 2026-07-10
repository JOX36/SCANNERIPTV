package org.jox3.iptvscanner.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "telegram_credentials")

class DataStoreManager(private val context: Context) {

    companion object {
        private val API_ID = stringPreferencesKey("api_id")
        private val API_HASH = stringPreferencesKey("api_hash")
        private val PHONE = stringPreferencesKey("phone")
        private val BOT_TOKEN = stringPreferencesKey("bot_token")
        private val CHAT_ID = stringPreferencesKey("chat_id")
    }

    suspend fun saveCredentials(
        apiId: String,
        apiHash: String,
        phone: String,
        botToken: String,
        chatId: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[API_ID] = apiId
            preferences[API_HASH] = apiHash
            preferences[PHONE] = phone
            preferences[BOT_TOKEN] = botToken
            preferences[CHAT_ID] = chatId
        }
    }

    fun getCredentials(): Flow<TelegramCredentials> {
        return context.dataStore.data.map { preferences ->
            TelegramCredentials(
                apiId = preferences[API_ID] ?: "",
                apiHash = preferences[API_HASH] ?: "",
                phone = preferences[PHONE] ?: "",
                botToken = preferences[BOT_TOKEN] ?: "",
                chatId = preferences[CHAT_ID] ?: ""
            )
        }
    }
}

data class TelegramCredentials(
    val apiId: String = "",
    val apiHash: String = "",
    val phone: String = "",
    val botToken: String = "",
    val chatId: String = ""
)
