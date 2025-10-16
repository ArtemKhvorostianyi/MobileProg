package com.example.emissions_calculator.data

/**
 * Дані для розрахунку емісій від різних видів палива
 */
data class EmissionsInputData(
    // Вугілля
    val bCoal: String = "1096363",
    val qCoal: String = "20.47",
    val kCoal: String = "25.20",
    
    // Мазут
    val bOil: String = "70945",
    val qOil: String = "39.48",
    val kOil: String = "0.15",
    
    // Природний газ
    val bGas: String = "84762",
    val qGas: String = "33.08",
    val kGas: String = "0.723"
)

/**
 * Результат розрахунків емісій
 */
data class CalculationResult(
    val isSuccess: Boolean = false,
    val result: String = "",
    val error: String? = null
)
