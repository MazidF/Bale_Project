package com.example.baleproject.ui.screens.profile

import com.example.baleproject.ui.screens.BaseEvents

interface ProfileEvents : BaseEvents {
    fun navigateToEdit()
    fun onLogoutCompleted()
}
