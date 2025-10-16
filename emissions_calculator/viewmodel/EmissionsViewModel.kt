package com.example.emissions_calculator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.emissions_calculator.data.CalculationResult
import com.example.emissions_calculator.data.EmissionsInputData
import com.example.emissions_calculator.domain.EmissionsCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel для калькулятора емісій
 */
class EmissionsViewModel : ViewModel() {
    
    // State для введених даних
    private val _inputData = MutableStateFlow(EmissionsInputData())
    val inputData: StateFlow<EmissionsInputData> = _inputData.asStateFlow()
    
    // State для результату розрахунків
    private val _calculationResult = MutableStateFlow(CalculationResult())
    val calculationResult: StateFlow<CalculationResult> = _calculationResult.asStateFlow()
    
    // Оновлення полів вугілля
    fun updateBCoal(value: String) {
        _inputData.update { it.copy(bCoal = value) }
    }
    
    fun updateQCoal(value: String) {
        _inputData.update { it.copy(qCoal = value) }
    }
    
    fun updateKCoal(value: String) {
        _inputData.update { it.copy(kCoal = value) }
    }
    
    // Оновлення полів мазуту
    fun updateBOil(value: String) {
        _inputData.update { it.copy(bOil = value) }
    }
    
    fun updateQOil(value: String) {
        _inputData.update { it.copy(qOil = value) }
    }
    
    fun updateKOil(value: String) {
        _inputData.update { it.copy(kOil = value) }
    }
    
    // Оновлення полів природного газу
    fun updateBGas(value: String) {
        _inputData.update { it.copy(bGas = value) }
    }
    
    fun updateQGas(value: String) {
        _inputData.update { it.copy(qGas = value) }
    }
    
    fun updateKGas(value: String) {
        _inputData.update { it.copy(kGas = value) }
    }
    
    // Виконати розрахунок
    fun calculate() {
        val result = EmissionsCalculator.calculate(_inputData.value)
        _calculationResult.value = result
    }
    
    // Очистити всі поля
    fun clear() {
        _inputData.value = EmissionsInputData()
        _calculationResult.value = CalculationResult()
    }
    
    // Встановити значення за замовчуванням
    fun resetToDefaults() {
        _inputData.value = EmissionsInputData()
        _calculationResult.value = CalculationResult()
    }
}
