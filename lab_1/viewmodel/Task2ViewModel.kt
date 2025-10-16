package com.example.lab_1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab_1.data.CalculationResult
import com.example.lab_1.data.Task2InputData
import com.example.lab_1.domain.Task2Calculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel для Завдання 2 - розрахунок параметрів мазуту
 */
class Task2ViewModel : ViewModel() {
    
    // State для введених даних
    private val _inputData = MutableStateFlow(Task2InputData())
    val inputData: StateFlow<Task2InputData> = _inputData.asStateFlow()
    
    // State для результату розрахунків
    private val _calculationResult = MutableStateFlow(CalculationResult())
    val calculationResult: StateFlow<CalculationResult> = _calculationResult.asStateFlow()
    
    // Оновлення полів вводу
    fun updateHg(value: String) {
        _inputData.update { it.copy(hg = value) }
    }
    
    fun updateCg(value: String) {
        _inputData.update { it.copy(cg = value) }
    }
    
    fun updateSg(value: String) {
        _inputData.update { it.copy(sg = value) }
    }
    
    fun updateOg(value: String) {
        _inputData.update { it.copy(og = value) }
    }
    
    fun updateVg(value: String) {
        _inputData.update { it.copy(vg = value) }
    }
    
    fun updateAg(value: String) {
        _inputData.update { it.copy(ag = value) }
    }
    
    fun updateWg(value: String) {
        _inputData.update { it.copy(wg = value) }
    }
    
    fun updateQdaf(value: String) {
        _inputData.update { it.copy(qdaf = value) }
    }
    
    // Виконати розрахунок
    fun calculate() {
        val result = Task2Calculator.calculate(_inputData.value)
        _calculationResult.value = result
    }
    
    // Очистити всі поля
    fun clear() {
        _inputData.value = Task2InputData()
        _calculationResult.value = CalculationResult()
    }
}

