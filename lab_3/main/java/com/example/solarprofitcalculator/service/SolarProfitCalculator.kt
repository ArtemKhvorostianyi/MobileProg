package com.example.solarprofitcalculator.service

import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt


class SolarProfitCalculator(
    private val p_cInput: Double,
    private val sigmaInput: Double,
    private val costValueInput: Double
) {

    fun p_d(p: Double, Pc: Double, sigma1: Double): Double {
        return (1 / (sigma1 * sqrt(2 * Math.PI))) *
                exp(-((p - Pc).pow(2)) / (2 * sigma1.pow(2)))
    }

    fun integrate(
        f: (Double) -> Double,
        from: Double,
        to: Double,
        steps: Int = 1000
    ): Double {
        val step = (to - from) / steps
        var area = 0.0
        for (i in 0 until steps) {
            val x1 = from + i * step
            val x2 = x1 + step
            area += 0.5 * (f(x1) + f(x2)) * step
        }
        return area
    }

    fun w_1(): Double {
        val from = 4.75
        val to = 5.25
        val result = integrate({ p -> p_d(p, p_cInput, sigmaInput) }, from, to)
        return p_cInput * 24 * result;
    }

    fun w_2(): Double {
        val from = 4.75
        val to = 5.25
        val result = integrate({ p -> p_d(p, p_cInput, sigmaInput) }, from, to)
        return p_cInput * 24 * (1 - result);
    }

    fun calculateIncome(): Double {
        return w_1() * costValueInput;
    }

    fun calculateFair(): Double {
        return w_2() * costValueInput;
    }

}