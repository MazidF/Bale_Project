package com.example.baleproject.ui.screens.signup

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import com.example.baleproject.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _signupResult = MutableStateFlow<Result<Unit>?>(null)
    val signupResult get() = _signupResult.asStateFlow()

    fun emailChecker(): Boolean {
        return email.isBlank() or Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun passwordChecker(): Boolean {
        return password.isBlank() or (password.trim().length >= 8)
    }

    fun signUp() {
        launch {
            useCase.signup(name, email, password).collect {
                _signupResult.emit(it)
            }
        }
    }
}