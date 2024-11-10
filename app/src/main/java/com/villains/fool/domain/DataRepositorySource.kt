package com.villains.fool.domain

interface DataRepositorySource {
    suspend fun reqDuplicateCheckSNSID(snsId: String)
    suspend fun reqJoin(snsID: String)
}