package org.jox3.iptvscanner.data

sealed class AuthState {
    object Idle : AuthState()
    object WaitingForPhone : AuthState()
    object WaitingForCode : AuthState()
    object WaitingForPassword : AuthState()
    object LoggedIn : AuthState()
    data class Error(val message: String) : AuthState()
}
