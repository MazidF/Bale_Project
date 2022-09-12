package com.example.baleproject.data.remote.api

import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.LabelId
import com.example.baleproject.data.model.RawLabel
import com.example.baleproject.utils.ACCESS_TOKEN_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LabelApi {
    @GET("labels")
    suspend fun getLabels(): Response<List<Label>>

    @POST("labels")
    suspend fun createLabel(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Body label: RawLabel,
    ): Response<LabelId>
}
