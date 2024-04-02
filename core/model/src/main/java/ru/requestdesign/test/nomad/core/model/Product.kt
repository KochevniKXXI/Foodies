package ru.requestdesign.test.nomad.core.model

data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val measure: Int,
    val measureUnit: String,
    val energyPer100Grams: Double,
    val proteinsPer100Grams: Double,
    val fatsPer100Grams: Double,
    val carbohydratesPer100Grams: Double,
    val tagIds: List<Int>,
)