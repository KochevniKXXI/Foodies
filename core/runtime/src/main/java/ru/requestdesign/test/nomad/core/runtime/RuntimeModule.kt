package ru.requestdesign.test.nomad.core.runtime

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RuntimeModule {
    @Binds
    @Singleton
    fun bindRuntimeDataSource(
        cart: Cart
    ): RuntimeDataSource
}