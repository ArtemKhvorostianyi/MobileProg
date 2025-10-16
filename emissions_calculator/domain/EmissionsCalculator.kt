package com.example.emissions_calculator.domain

import com.example.emissions_calculator.data.CalculationResult
import com.example.emissions_calculator.data.EmissionsInputData

/**
 * Калькулятор емісій від різних видів палива
 */
object EmissionsCalculator {
    
    fun calculate(input: EmissionsInputData): CalculationResult {
        return try {
            // Безпечне перетворення тексту в числа
            val bC = input.bCoal.toDoubleOrNull() ?: 0.0
            val bO = input.bOil.toDoubleOrNull() ?: 0.0
            val bG = input.bGas.toDoubleOrNull() ?: 0.0

            val qC = input.qCoal.toDoubleOrNull() ?: 0.0
            val qO = input.qOil.toDoubleOrNull() ?: 0.0
            val qG = input.qGas.toDoubleOrNull() ?: 0.0

            val kC = input.kCoal.toDoubleOrNull() ?: 0.0
            val kO = input.kOil.toDoubleOrNull() ?: 0.0
            val kG = input.kGas.toDoubleOrNull() ?: 0.0
            
            // Перевірка валідності даних
            if (qC <= 0 || qO <= 0 || qG <= 0) {
                return CalculationResult(
                    isSuccess = false,
                    error = "Теплота згорання має бути більше 0"
                )
            }
            
            if (kC < 0 || kO < 0 || kG < 0) {
                return CalculationResult(
                    isSuccess = false,
                    error = "Вміст золи не може бути від'ємним"
                )
            }

            // Розрахунки емісій
            val colaK = ((1000000 / qC) * 0.8 * (kC / (100 - 1.5)) * (1 - 0.985))
            val oilK = ((1000000 / qO) * 1 * (kO / (100 - 0)) * (1 - 0.985))
            val coalEmission = (0.000001) * colaK * qC * bC
            val oilEmission = (0.000001) * oilK * qO * bO

            val result = """
                1.1 Показник емісії твердих частинок при спалюванні вугілля: 
                 ${"%.2f".format(colaK)} г/ГДж
                
                1.2 Валовий викид при спалюванні вугілля: 
                ${"%.2f".format(coalEmission)} т.
                
                1.3 Показник емісії твердих частинок при спалюванні мазуту:
                ${"%.2f".format(oilK)} г/ГДж
                
                1.4 Валовий викид при спалюванні мазуту:
                ${"%.2f".format(oilEmission)} т.
                
                1.5 Показник емісії твердих частинок при спалюванні природного газу:
                0 г/ГДж
                
                1.6 Валовий викид при спалюванні природного газу:
                0 т.
            """.trimIndent()
            
            CalculationResult(
                isSuccess = true,
                result = result
            )
        } catch (e: Exception) {
            CalculationResult(
                isSuccess = false,
                error = "Помилка розрахунку: ${e.message}"
            )
        }
    }
}
