package com.thegreatdawn.bizzapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.auth.AuthResult
import com.thegreatdawn.bizzapp.database.AuthRepository
import com.thegreatdawn.bizzapp.database.UserNode

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val context: Context
) : ViewModel() {


    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // MutableState untuk menangani hasil sign-up
    private val _authResult = mutableStateOf<AuthResult?>(null)
    val authResult: State<AuthResult?> = _authResult


    fun signUpWithEmailAndPassword(username: String, email: String, password: String) {
        _isLoading.value = true
        authRepository.signUpWithEmailAndPassword(username,email, password) { isSuccess, exception ->
            _isLoading.value = false
            if (isSuccess) {
                _authResult.value = AuthResult.Success
                _isLoginVisible.value = true
                _isSignUpVisible.value = false
                Toast.makeText(context, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
            } else {
                _authResult.value = AuthResult.Failure(exception)
                Toast.makeText(context, exception!!.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun signInWithEmailAndPassword(email: String, pw: String, navController: NavController) {
        _isLoading.value = true
        authRepository.signInWithEmailAndPassword(email, pw) { isSuccess, exception ->
            _isLoading.value = false
            if (isSuccess) {
                navController.navigate("home") {
                    popUpTo("auth") {
                        inclusive = true
                    }
                }
                _authResult.value = AuthResult.Success
            } else {
                _authResult.value = AuthResult.Failure(exception)
                Toast.makeText(context, exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val _userNode = mutableStateOf<UserNode?>(null)
    val userNode : State<UserNode?> = _userNode
    fun fetchUsername(){
        authRepository.getUsername { username, email ->
            if (username != null && email !=null) {
                _userNode.value = UserNode(username, email)
            }
        }
    }


    fun getLoginStatus(): Boolean {
        return authRepository.isLoggedIn()
    }


    fun signOut() {
        authRepository.signOut()

    }

    fun geuUserId(): String? {
        return authRepository.getCurrentUserUid()
    }


    private val _isLoginVisible = mutableStateOf(false)
    val isLoginVisible get() = _isLoginVisible

    fun setLoginVisible(value: Boolean) {
        _isLoginVisible.value = value
    }

    private val _isSignUpVisible = mutableStateOf(false)
    val isSignUpVisible get() = _isSignUpVisible

    fun setSignUpVisible(value: Boolean) {
        _isSignUpVisible.value = value
    }


}