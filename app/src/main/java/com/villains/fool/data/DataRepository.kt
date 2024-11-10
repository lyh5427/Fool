package com.villains.fool.data

import com.villains.fool.domain.DataRepositorySource
import com.villains.fool.domain.RestDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private var rest: RestDataSource
) : DataRepositorySource {
    override suspend fun reqDuplicateCheckSNSID(snsId: String) {
        rest.reqDuplicateCheckSNSID("Aa")
    }

    override suspend fun reqJoin(snsID: String) {
        rest.reqJoin(snsID)
    }
}