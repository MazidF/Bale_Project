package com.example.baleproject.ui

import androidx.lifecycle.ViewModel
import com.example.baleproject.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    fun hasBeenLoggedIn(): Boolean {
        return useCase.hasBeenLoggedIn()
    }
}
