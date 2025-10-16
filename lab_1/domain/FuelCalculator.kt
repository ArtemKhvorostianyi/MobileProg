package com.example.lab_1.domain

import com.example.lab_1.data.CalculationResult
import com.example.lab_1.data.Task1InputData

/**
 * Калькулятор для Завдання 1 - розрахунок параметрів палива
 */
object Task1Calculator {
    
    fun calculate(input: Task1InputData): CalculationResult {
        return try {
            val hVal = input.hp.toDoubleOrNull() ?: 0.0
            val cVal = input.c.toDoubleOrNull() ?: 0.0
            val sVal = input.sp.toDoubleOrNull() ?: 0.0
            val nVal = input.np.toDoubleOrNull() ?: 0.0
            val oVal = input.op.toDoubleOrNull() ?: 0.0
            val aVal = input.ap.toDoubleOrNull() ?: 0.0
            val wVal = input.w.toDoubleOrNull() ?: 0.0
            
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
            
            val kpc = 100 / (100 - wVal)
            val krg = 100 / (100 - wVal - aVal)
            
            val qpn = (339 * cVal) + (1030 * hVal) - (108.8 * (oVal - sVal)) - (25 * wVal)
            val qcn = ((qpn / 1000) + 0.025 * wVal) * (100 / (100 - wVal))
            val qgn = ((qpn / 1000) + 0.025 * wVal) * (100 / (100 - wVal - aVal))
            
            val result = """
                1.1 Коефіцієнт переходу від робочої до сухої маси 
                Кпс = ${"%.2f".format(kpc)}
                
                1.2 Коефіцієнт переходу від робочої до горючої маси 
                Кпг = ${"%.2f".format(krg)}
                
                1.3 Склад сухої маси палива
                Hc = ${"%.2f".format((hVal * kpc))} %
                Cc = ${"%.2f".format((cVal * kpc))} %
                Sc = ${"%.2f".format((sVal * kpc))} %
                Nc = ${"%.2f".format((nVal * kpc))} %
                Oc = ${"%.2f".format((oVal * kpc))} %
                Ac = ${"%.2f".format((aVal * kpc))} %
                
                1.4 Склад горючої маси палива
                Hг = ${"%.2f".format((hVal * krg))} %
                Cг = ${"%.2f".format((cVal * krg))} %
                Sг = ${"%.2f".format((sVal * krg))} %
                Nг = ${"%.2f".format((nVal * krg))} %
                Oг = ${"%.2f".format((oVal * krg))} %
                
                1.5 Нижча теплота згорання для робочої маси 
                QpН = ${"%.2f".format((qpn))} [кДж/кг] = ${"%.2f".format((qpn / 1000))} [МДж/кг]
                
                1.6 Нижча теплота згоряння для сухої маси
                ${"%.2f".format((qcn))} [МДж/кг]
                
                1.7 Нижча теплота згоряння для горючої маси
                ${"%.2f".format((qgn))} [МДж/кг]
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

