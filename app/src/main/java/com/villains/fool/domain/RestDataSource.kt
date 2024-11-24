package com.villains.fool.domain

import com.villains.fool.domain.di.dto.remote.ExchangeRateVo
import com.villains.fool.domain.di.dto.remote.Response

interface RestDataSource {
    suspend fun reqDuplicateCheckSNSID(snsID: String): Response
    suspend fun reqJoin(snsID: String): Response
    suspend fun reqLogin(snsID: String): Response
    suspend fun getExchangeRate(today: String): Response
}