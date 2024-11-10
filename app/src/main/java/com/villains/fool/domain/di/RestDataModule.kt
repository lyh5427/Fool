package com.villains.fool.domain.di

import com.villains.fool.data.DataRepository
import com.villains.fool.data.RestData
import com.villains.fool.domain.DataRepositorySource
import com.villains.fool.domain.RestDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RestDataModule {

    @Singleton
    @Binds
    abstract fun bindsRestData(restData: RestData): RestDataSource

    @Singleton
    @Binds
    abstract fun bindsDataRepo(repo: DataRepository): DataRepositorySource
}