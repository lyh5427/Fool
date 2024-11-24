package com.villains.fool.data

import android.util.Log
import com.villains.fool.domain.DataRepositorySource
import com.villains.fool.domain.RestDataSource
import com.villains.fool.domain.di.dto.remote.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private var rest: RestDataSource
) : DataRepositorySource {
    override suspend fun reqDuplicateCheckSNSID(snsId: String): Flow<Response> {
        return flow {
            emit(rest.reqDuplicateCheckSNSID(snsId))
        }

    }

    override suspend fun reqJoin(snsID: String): Flow<Response> {
        return flow {
            emit(rest.reqJoin(snsID))
        }
    }

    override suspend fun reqLogin(snsId: String): Flow<Response> {
        return flow {
            emit(rest.reqLogin(snsId))
        }
    }

    override suspend fun getExchangeRate(today: String): Flow<Response> {

        Log.d("Tag", "bbbbbbbbbbbbb")
        return flow {
                emit(rest.getExchangeRate(today))
        }
    }
}