package com.villains.fool.domain

import com.villains.fool.domain.di.dto.remote.Response
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun reqDuplicateCheckSNSID(snsId: String): Flow<Response>
    suspend fun reqJoin(snsID: String): Flow<Response>
    suspend fun reqLogin(snsId: String): Flow<Response>
    suspend fun getExchangeRate(today: String): Flow<Response>
}