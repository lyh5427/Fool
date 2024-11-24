package com.villains.fool.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.villains.fool.Application
import com.villains.fool.domain.DataRepositorySource
import com.villains.fool.domain.di.dto.remote.ExchangeRateVo
import com.villains.fool.domain.di.dto.remote.OnResponse
import com.villains.fool.domain.di.dto.remote.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: DataRepositorySource
): ViewModel() {
    fun reqExchageRate() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.getExchangeRate("20241121").collectLatest {
                when (it) {
                    is Response.Success -> {
                        Application.prefs.exchangeRate.clear()
                        val userListType = object : TypeToken<OnResponse<List<ExchangeRateVo>>>() {}.type
                        val users: OnResponse<List<ExchangeRateVo>> = Gson().fromJson(it.response as String, userListType)

                        users.data!!.forEach {
                            val ctrCode = Application.prefs.currencyToCountry[it.ctrDivCd]?: "unknown"
                            Application.prefs.exchangeRate.set(ctrCode, it.exgrBaseRate)

                            Log.d("Tag", "${it.ctrDivCd} ${ctrCode}   ${it.exgrBaseRate}")
                        }

                        Log.d("Tag", "${it.response}")
                    }

                    is Response.Fail -> {
                        Log.d("Tag", "${it.err}")
                    }
                }
            }
        }
    }
}