package com.example.lab_1.domain

import com.example.lab_1.data.CalculationResult
import com.example.lab_1.data.Task2InputData

/**
 * Калькулятор для Завдання 2 - розрахунок параметрів мазуту
 */
object Task2Calculator {
    
    fun calculate(input: Task2InputData): CalculationResult {
        return try {
            val hVal = input.hg.toDoubleOrNull() ?: 0.0
            val cVal = input.cg.toDoubleOrNull() ?: 0.0
            val sVal = input.sg.toDoubleOrNull() ?: 0.0
            val oVal = input.og.toDoubleOrNull() ?: 0.0
            val vVal = input.vg.toDoubleOrNull() ?: 0.0
            val wVal = input.wg.toDoubleOrNull() ?: 0.0
            val aVal = input.ag.toDoubleOrNull() ?: 0.0
            val qVal = input.qdaf.toDoubleOrNull() ?: 0.0
            
            // Перевірка валідності даних
            if (wVal >= 100) {
                return CalculationResult(
                    isSuccess = false,
                    error = "Значення W має бути менше 100"
                )
            }
            
            if (wVal + aVal >= 100) {
                return CalculationResult(
                    isSuccess = false,
                    error = "Сума W + A має бути менше 100"
                )
            }
            
            val Qri = qVal * ((100 - wVal - aVal) / 100) - (0.025 * wVal)
            
            val result = """
                2.1 Склад робочої маси мазуту становитеме
                H = ${"%.2f".format((hVal * ((100 - wVal - aVal) / 100)))} %
                C = ${"%.2f".format((cVal * ((100 - wVal - aVal) / 100)))} %
                S = ${"%.2f".format((sVal * ((100 - wVal - aVal) / 100)))} %
                O = ${"%.2f".format((oVal * ((100 - wVal - aVal) / 100)))} %
                V = ${"%.2f".format((vVal * ((100 - wVal) / 100)))} мг/кг
                A = ${"%.2f".format((aVal * ((100 - wVal) / 100)))} %
                
                2.2 Нижча теплота згорання мазуту
                ${"%.2f".format(Qri)} МДж/кг
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

