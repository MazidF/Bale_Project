package com.example.baleproject.ui.screens.home

import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.ui.screens.BaseEvents

interface HomeEvents : BaseEvents {
    fun navigateToDetailPage(issue: IssueItem)
    fun navigateToFeedbackPage()
    fun navigateToLoginPage()
}