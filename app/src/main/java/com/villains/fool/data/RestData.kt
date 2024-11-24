package com.villains.fool.data

import android.content.Context
import android.graphics.Paint.Join
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.villains.fool.Application
import com.villains.fool.domain.ApiInterface
import com.villains.fool.domain.RestDataSource
import com.villains.fool.domain.di.dto.remote.ExchangeRateVo
import com.villains.fool.domain.di.dto.remote.JoinBody
import com.villains.fool.domain.di.dto.remote.LoginVo
import com.villains.fool.domain.di.dto.remote.OnResponse
import com.villains.fool.domain.di.dto.remote.ReqLogin
import com.villains.fool.domain.di.dto.remote.Response
import com.villains.fool.domain.di.dto.remote.Test
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestData @Inject constructor(
    private val rest: ApiInterface,
    @ApplicationContext private val context: Context,
) : RestDataSource {
    override suspend fun reqDuplicateCheckSNSID(snsID: String): Response {
        val result = rest.duplicateCheck(Test())
        Log.d(Application.TAG, "wwwwwwwww")

        return if (result.isSuccessful) {
            val resultString = result.body()!!.string()
            result.message()

            Log.d("Tag", " ${resultString}")

            Response.Success("")
        } else {
            Response.Fail("")
        }
    }

    override suspend fun reqJoin(snsID: String): Response {
        val result = rest.joinUser(JoinBody(
            snsId = snsID
        ))

        return if (result.isSuccessful) {
            val resultString = result.body()!!.string()

            Log.d("Tag", "${resultString}")

            Response.Success("")
        } else {
            Response.Fail("fail")
        }
    }

    override suspend fun reqLogin(snsID: String): Response {
        val result = rest.reqLogin(ReqLogin(snsId = Application.prefs.snsId))

        return if (result.isSuccessful) {
            val resultString = result.body()!!.string()

            Response.Success(resultString)
        } else {
            Response.Fail(result.message())
        }
    }

    override suspend fun getExchangeRate(today: String): Response {
        val result = rest.getExchangeRate(today)

        return if (result.isSuccessful) {
            val resultString = result.body()!!.string()

            Log.d("Tag", "${resultString}")

            Response.Success(resultString)
        } else {

            Log.d("Tag", "${result.message()}")

            Response.Fail("fail")
        }
    }
}