package com.example.baleproject.ui.screens.signup

import com.example.baleproject.ui.screens.BaseEvents

interface SignupEvents : BaseEvents {
    fun onSignupCompleted(email: String, password: String)
    fun navigateToLogin()
}