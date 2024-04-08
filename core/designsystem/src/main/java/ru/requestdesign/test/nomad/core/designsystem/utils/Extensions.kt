package ru.requestdesign.test.nomad.core.designsystem.utils

import java.math.RoundingMode
import java.text.NumberFormat

/**
 * Преобразует [Int] в строку вида: «123 ₽»
 */
fun Int.formatAsPriceString(): String {
    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
        roundingMode = RoundingMode.CEILING
    }
    return formatter.format(this / 100.00)
}