package com.example.baleproject.ui.screens.home

import com.example.baleproject.data.model.Issue
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.ui.screens.BaseEvents

interface HomeEvents : BaseEvents {
    fun navigateToDetailPage(issue: Issue)
    fun navigateToFeedbackPage()
    fun navigateToLoginPage()
}