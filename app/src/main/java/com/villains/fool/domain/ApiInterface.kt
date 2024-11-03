package com.villains.fool.domain

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("/")
    suspend fun reqRestApi(@Body parameters: String): Response<ResponseBody>
}