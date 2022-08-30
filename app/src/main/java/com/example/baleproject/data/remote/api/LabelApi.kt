package com.example.baleproject.data.remote.api

import com.example.baleproject.data.model.Label
import retrofit2.Response
import retrofit2.http.GET

interface LabelApi {
    @GET("labels")
    suspend fun getLabels(): Response<List<Label>>

    // TODO: add createLabel for Admin
}
