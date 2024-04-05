package ru.requestdesign.test.nomad.core.model

data class Product(
    val id: Int = 0,
    val categoryId: Int = 0,
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val priceCurrent: Int = 0,
    val priceOld: Int? = null,
    val measure: Int = 0,
    val measureUnit: String = "",
    val energyPer100Grams: Double = 0.0,
    val proteinsPer100Grams: Double = 0.0,
    val fatsPer100Grams: Double = 0.0,
    val carbohydratesPer100Grams: Double = 0.0,
    val tagIds: List<Int> = emptyList(),
)