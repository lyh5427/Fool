package com.villains.fool.data

import android.content.Context
import android.graphics.Paint.Join
import android.util.Log
import com.villains.fool.Application
import com.villains.fool.domain.ApiInterface
import com.villains.fool.domain.RestDataSource
import com.villains.fool.domain.di.dto.remote.JoinBody
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
    override suspend fun reqDuplicateCheckSNSID(snsID: String) {
        val result = rest.duplicateCheck(Test())
        Log.d(Application.TAG, "wwwwwwwww")

        if (result.isSuccessful) {
            val response = result.body()?.string()?: "aa"

            Log.d(Application.TAG, "${response}")
        }

    }

    override suspend fun reqJoin(snsID: String) {
        val result = rest.joinUser(JoinBody(
            snsId = snsID
        ))
    }
}