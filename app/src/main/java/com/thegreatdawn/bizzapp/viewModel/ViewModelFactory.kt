package com.thegreatdawn.bizzapp.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thegreatdawn.bizzapp.database.AuthRepository

class MyViewModelFactory(private val authRepository: AuthRepository, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository, context ) as T
        }
        else if (modelClass.isAssignableFrom(BriLinkViewModel::class.java)) {
            return BriLinkViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}