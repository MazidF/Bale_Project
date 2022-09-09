package com.example.baleproject.ui.screens.login

import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.screens.BaseEvents

interface LoginEvents : BaseEvents {
    fun onLoginCompleted(info: UserInfo)
    fun navigateToSignupScreen()
    fun getEmailAndPassword(): Pair<String, String>
}
