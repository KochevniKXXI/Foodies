package ru.requestdesign.test.nomad.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.requestdesign.test.nomad.core.data.repository.CategoriesRepository
import ru.requestdesign.test.nomad.core.data.repository.NetworkCategoriesRepository
import ru.requestdesign.test.nomad.core.data.repository.NetworkProductsRepository
import ru.requestdesign.test.nomad.core.data.repository.NetworkTagsRepository
import ru.requestdesign.test.nomad.core.data.repository.ProductsRepository
import ru.requestdesign.test.nomad.core.data.repository.TagsRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun bindProductsRepository(
        networkProductsRepository: NetworkProductsRepository
    ): ProductsRepository

    @Binds
    abstract fun bindCategoriesRepository(
        networkCategoriesRepository: NetworkCategoriesRepository
    ): CategoriesRepository

    @Binds
    abstract fun bindTagsRepository(
        networkTagsRepository: NetworkTagsRepository
    ): TagsRepository
}