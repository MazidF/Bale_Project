package com.example.baleproject.ui.screens.profile.edit

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.baleproject.data.model.User
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import com.example.baleproject.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    var name by mutableStateOf(useCase.getUserInfo().name)
    var email by mutableStateOf(useCase.getUserInfo().email)

    private val _updateResult = MutableStateFlow<Result<User>?>(null)
    val updateResult get() = _updateResult.asStateFlow()

    fun emailChecker(): Boolean {
        return email.isBlank() or Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun update() {
        launch {
            useCase.updateUserInfo(name, email).collect {
                _updateResult.emit(it)
            }
        }
    }
}