package com.thegreatdawn.bizzapp.auth

import java.lang.Exception

sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val exception: Exception?) : AuthResult()
}