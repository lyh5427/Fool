package com.villains.fool.data

import android.util.Log
import com.villains.fool.domain.DataRepositorySource
import com.villains.fool.domain.RestDataSource
import com.villains.fool.domain.di.dto.remote.ReqJoinVo
import com.villains.fool.domain.di.dto.remote.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private var rest: RestDataSource
) : DataRepositorySource {
    override suspend fun reqDuplicateCheckSNSID(body: ReqJoinVo): Flow<Response> {
        return flow {
            emit(rest.reqDuplicateCheckSNSID(body))
        }

    }

    override suspend fun reqJoin(body: ReqJoinVo): Flow<Response> {
        return flow {
            emit(rest.reqJoin(body))
        }
    }

    override suspend fun reqLogin(body: ReqJoinVo): Flow<Response> {
        return flow {
            emit(rest.reqLogin(body))
        }
    }

    override suspend fun getExchangeRate(today: String): Flow<Response> {

        Log.d("Tag", "bbbbbbbbbbbbb")
        return flow {
                emit(rest.getExchangeRate(today))
        }
    }
}