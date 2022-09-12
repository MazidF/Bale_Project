package com.example.baleproject.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import com.example.baleproject.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    private val _autoLoginState = MutableStateFlow<Result<Unit>>(Result.loading())
    val autoLoginState get() = _autoLoginState.asStateFlow()

    fun autoLogin() {
        launch {
            useCase.autoLogin().collect {
                _autoLoginState.emit(it)
            }
        }
    }
}
