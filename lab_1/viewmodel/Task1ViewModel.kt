package com.example.lab_1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab_1.data.CalculationResult
import com.example.lab_1.data.Task1InputData
import com.example.lab_1.domain.Task1Calculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel для Завдання 1 - розрахунок параметрів палива
 */
class Task1ViewModel : ViewModel() {
    
    // State для введених даних
    private val _inputData = MutableStateFlow(Task1InputData())
    val inputData: StateFlow<Task1InputData> = _inputData.asStateFlow()
    
    // State для результату розрахунків
    private val _calculationResult = MutableStateFlow(CalculationResult())
    val calculationResult: StateFlow<CalculationResult> = _calculationResult.asStateFlow()
    
    // Оновлення полів вводу
    fun updateHp(value: String) {
        _inputData.update { it.copy(hp = value) }
    }
    
    fun updateC(value: String) {
        _inputData.update { it.copy(c = value) }
    }
    
    fun updateSp(value: String) {
        _inputData.update { it.copy(sp = value) }
    }
    
    fun updateNp(value: String) {
        _inputData.update { it.copy(np = value) }
    }
    
    fun updateOp(value: String) {
        _inputData.update { it.copy(op = value) }
    }
    
    fun updateAp(value: String) {
        _inputData.update { it.copy(ap = value) }
    }
    
    fun updateW(value: String) {
        _inputData.update { it.copy(w = value) }
    }
    
    // Виконати розрахунок
    fun calculate() {
        val result = Task1Calculator.calculate(_inputData.value)
        _calculationResult.value = result
    }
    
    // Очистити всі поля
    fun clear() {
        _inputData.value = Task1InputData()
        _calculationResult.value = CalculationResult()
    }
}

