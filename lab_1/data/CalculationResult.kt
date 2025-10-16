package com.example.lab_1.data

/**
 * Результат розрахунків
 */
data class CalculationResult(
    val isSuccess: Boolean = false,
    val result: String = "",
    val error: String? = null
)

