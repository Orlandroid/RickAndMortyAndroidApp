package com.example.paggingexample.data.state

sealed class SessionStatus() {
    object LOADING : SessionStatus()
    object NETWORKERROR : SessionStatus()
    object SUCESS : SessionStatus()
    object ERROR : SessionStatus()
}
