package com.example.baleproject.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.baleproject.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    val userInfo = useCase.getUserInfo()

    fun logout() {
        useCase.logout()
    }
}
