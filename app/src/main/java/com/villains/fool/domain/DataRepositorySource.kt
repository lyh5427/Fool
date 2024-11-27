package com.villains.fool.domain

import com.villains.fool.domain.di.dto.remote.ReqJoinVo
import com.villains.fool.domain.di.dto.remote.Response
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun reqDuplicateCheckSNSID(body: ReqJoinVo): Flow<Response>
    suspend fun reqJoin(body: ReqJoinVo): Flow<Response>
    suspend fun reqLogin(body: ReqJoinVo): Flow<Response>
    suspend fun getExchangeRate(today: String): Flow<Response>
}