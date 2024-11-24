package com.villains.fool.domain

import com.villains.fool.domain.di.dto.remote.JoinBody
import com.villains.fool.domain.di.dto.remote.LoginVo
import com.villains.fool.domain.di.dto.remote.ReqLogin
import com.villains.fool.domain.di.dto.remote.Test
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("/")
    suspend fun reqRestApi(@Body parameters: String): Response<ResponseBody>

    @POST("/auth/join")
    suspend fun joinUser(@Body parameters: JoinBody): Response<ResponseBody>

    @POST("/auth/duplicate-check/sns-id")
    @Headers("accept: application/json",
        "content-type: application/json"
    )
    suspend fun duplicateCheck(
        @Body body: Test
    ): Response<ResponseBody>

    // 환율 정보 가져오기
    @GET("/exchangerate/{today}")
    suspend fun getExchangeRate(
        @Path("today") today: String
    ): Response<ResponseBody>

    @POST("/auth/login")
    @Headers("accept: application/json",
        "content-type: application/json"
    )
    suspend fun reqLogin(
        @Body body: ReqLogin
    ): Response<ResponseBody>
}
