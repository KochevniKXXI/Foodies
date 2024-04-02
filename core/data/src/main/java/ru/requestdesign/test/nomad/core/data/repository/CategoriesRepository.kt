package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.model.Category

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
}