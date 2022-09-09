package com.example.baleproject.ui.screens.detail

import com.example.baleproject.ui.screens.BaseEvents

interface DetailEvents : BaseEvents {
    fun getIssueId(): String
    fun goToLoginPage()
}
