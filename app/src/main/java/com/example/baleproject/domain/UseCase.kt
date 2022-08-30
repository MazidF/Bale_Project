package com.example.baleproject.domain

import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.User
import com.example.baleproject.data.repository.IssueRepository
import com.example.baleproject.data.repository.LabelRepository
import com.example.baleproject.data.repository.UserRepository
import com.example.baleproject.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class UseCase(
    private val userRepository: UserRepository,
    private val labelRepository: LabelRepository,
    private val issueRepository: IssueRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    private val scope by lazy {
        CoroutineScope(dispatcher + SupervisorJob())
    }

    private lateinit var user: User
    private var labels: List<Label> = emptyList()

    init {
        initialSetup()
    }

    private fun initialSetup() {
        loadLabels()
    }

    private fun loadLabels() = scope.launch {
        repeat(3) { // try 3 times to retrieve labels
            val result = labelRepository.getLabels()
            if (result is Result.Success) {
                labels = result.data()
                return@launch
            }
        }
    }

    fun userRepository(block: UserRepository.() -> Unit) {
        userRepository.block()
    }

    fun labelRepository(block: LabelRepository.() -> Unit) {
        labelRepository.block()
    }

    fun issueRepository(block: IssueRepository.() -> Unit) {
        issueRepository.block()
    }

    suspend fun userRepository(block: suspend UserRepository.() -> Unit) {
        userRepository.block()
    }

    suspend fun labelRepository(block: suspend LabelRepository.() -> Unit) {
        labelRepository.block()
    }

    suspend fun issueRepository(block: suspend IssueRepository.() -> Unit) {
        issueRepository.block()
    }
}