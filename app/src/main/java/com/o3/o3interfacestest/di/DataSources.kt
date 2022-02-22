package com.o3.o3interfacestest.di

import android.content.Context
import com.o3.o3interfacestest.data.source.local.db.AppDatabase
import com.o3.o3interfacestest.data.source.remote.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataSources @Inject constructor(@ApplicationContext var context: Context,
                                      private val service: ApiService,
                                      private val localDb: AppDatabase) {
    fun sourceOfTruthNetworkDB(): ApiService {
        return service
    }

    fun sourceOfTruthLocalDB(): AppDatabase {
        return localDb
    }
}