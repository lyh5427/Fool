package com.villains.fool.domain

interface RestDataSource {
    suspend fun reqDuplicateCheckSNSID(snsID: String)
    suspend fun reqJoin(snsID: String)
}