package com.example.citassalon.data.state

sealed class SessionStatus() {
    object LOADING : SessionStatus()
    object NETWORKERROR : SessionStatus()
    object SUCESS : SessionStatus()
    object ERROR : SessionStatus()
}
