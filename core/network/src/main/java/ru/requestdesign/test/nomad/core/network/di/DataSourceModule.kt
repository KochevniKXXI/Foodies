package ru.requestdesign.test.nomad.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.requestdesign.test.nomad.core.network.NetworkDatasource
import ru.requestdesign.test.nomad.core.network.retrofit.RetrofitWorkTestServer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindNetworkDataSource(
        retrofitWorkTestServer: RetrofitWorkTestServer
    ): NetworkDatasource
}