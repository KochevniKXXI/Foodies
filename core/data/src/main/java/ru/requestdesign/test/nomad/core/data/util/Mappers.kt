package ru.requestdesign.test.nomad.core.data.util

import ru.requestdesign.test.nomad.core.model.Category
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.model.Tag
import ru.requestdesign.test.nomad.core.network.data.NetworkCategory
import ru.requestdesign.test.nomad.core.network.data.NetworkProduct
import ru.requestdesign.test.nomad.core.network.data.NetworkTag

/*
 * Здесь находяться преобразователи данных в модель приложения
 */

fun NetworkProduct.asInternalModel() = Product(
    id = id,
    categoryId = categoryId,
    name = name,
    description = description,
    image = image,
    priceCurrent = priceCurrent,
    priceOld = priceOld,
    measure = measure,
    measureUnit = measureUnit,
    energyPer100Grams = energyPer100Grams,
    proteinsPer100Grams = proteinsPer100Grams,
    fatsPer100Grams = fatsPer100Grams,
    carbohydratesPer100Grams = carbohydratesPer100Grams,
    tagIds = tagIds,
)

fun NetworkCategory.asInternalModel() = Category(
    id = id,
    name = name,
)

fun NetworkTag.asInternalModel() = Tag(
    id = id,
    name = name,
)