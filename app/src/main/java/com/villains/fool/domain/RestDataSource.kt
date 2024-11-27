package com.villains.fool.domain

import com.villains.fool.domain.di.dto.remote.ExchangeRateVo
import com.villains.fool.domain.di.dto.remote.ReqJoinVo
import com.villains.fool.domain.di.dto.remote.Response

interface RestDataSource {
    suspend fun reqDuplicateCheckSNSID(body: ReqJoinVo): Response
    suspend fun reqJoin(body: ReqJoinVo): Response
    suspend fun reqLogin(body: ReqJoinVo): Response
    suspend fun getExchangeRate(today: String): Response
}