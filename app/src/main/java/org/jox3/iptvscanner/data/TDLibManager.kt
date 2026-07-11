package org.jox3.iptvscanner.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi

class TDLibManager {

    private var client: Client? = null

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private var phoneNumber: String = ""

    fun initialize() {
        if (client != null) return

        client = Client.create(
            { update -> handleUpdate(update) },
            null,
            null
        )

        // Configuración básica
        client?.send(TdApi.SetLogVerbosityLevel(1), null)
    }

    private fun handleUpdate(update: TdApi.Object) {
        when (update.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                val state = (update as TdApi.UpdateAuthorizationState).authorizationState
                handleAuthorizationState(state)
            }
        }
    }

    private fun handleAuthorizationState(state: TdApi.AuthorizationState) {
        when (state.constructor) {
            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                _authState.value = AuthState.WaitingForPhone
            }
            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                _authState.value = AuthState.WaitingForCode
            }
            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                _authState.value = AuthState.WaitingForPassword
            }
            TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                _authState.value = AuthState.LoggedIn
            }
            TdApi.AuthorizationStateClosed.CONSTRUCTOR -> {
                _authState.value = AuthState.Error("Sesión cerrada")
            }
        }
    }

    suspend fun sendPhoneNumber(phone: String) {
        this.phoneNumber = phone
        withContext(Dispatchers.IO) {
            client?.send(TdApi.SetAuthenticationPhoneNumber(phone, null), null)
        }
    }

    suspend fun sendCode(code: String) {
        withContext(Dispatchers.IO) {
            client?.send(TdApi.CheckAuthenticationCode(code), null)
        }
    }

    suspend fun sendPassword(password: String) {
        withContext(Dispatchers.IO) {
            client?.send(TdApi.CheckAuthenticationPassword(password), null)
        }
    }

    fun getClient(): Client? = client

    fun isLoggedIn(): Boolean {
        return _authState.value is AuthState.LoggedIn
    }
}
