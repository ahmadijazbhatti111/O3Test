package com.o3.o3interfacestest.di

import com.o3.o3interfacestest.domain.repository.DataRepository
import com.o3.o3interfacestest.data.repository.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @ViewModelScoped
    @Provides
    fun getDataRepoImpl(dataSources: DataSources): DataRepository {
        return DataRepositoryImpl(dataSources)
    }
}

