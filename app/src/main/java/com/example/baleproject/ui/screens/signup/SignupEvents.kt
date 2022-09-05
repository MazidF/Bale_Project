package com.example.baleproject.ui.screens.signup

import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.screens.BaseEvents

interface SignupEvents : BaseEvents {
    fun onSignupCompleted()
    fun navigateToLogin()
}