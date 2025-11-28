package com.example.solarprofitcalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.solarprofitcalculator.service.SolarProfitCalculator
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var incomeResultText: TextView
    private lateinit var fairResultText: TextView
    private lateinit var resultText: TextView
    private lateinit var calculateAgainButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)

        incomeResultText = findViewById(R.id.incomeResultText)
        fairResultText = findViewById(R.id.fairResultText)
        resultText = findViewById(R.id.resultText)
        calculateAgainButton = findViewById(R.id.calculateAgainButton)

        val powerValue = intent.getDoubleExtra("powerValue", 0.0)
        val sigmaValue = intent.getDoubleExtra("sigmaValue", 0.0)
        val costValue = intent.getDoubleExtra("costValue", 0.0)

        val solarProfitCalculator = SolarProfitCalculator(
            p_cInput = powerValue,
            sigmaInput = sigmaValue,
            costValueInput = costValue,
        )

        val incomeResult = solarProfitCalculator.calculateIncome()
        val fairResult = solarProfitCalculator.calculateFair()

        // Format numbers with 2 decimal places
        val decimalFormat = DecimalFormat("#,##0.00")
        
        incomeResultText.text = decimalFormat.format(incomeResult)
        fairResultText.text = decimalFormat.format(fairResult)
        
        resultText.text = """
            Input Parameters:
            • Power (P): ${decimalFormat.format(powerValue)}
            • Sigma (σ): ${decimalFormat.format(sigmaValue)}
            • Cost (B): ${decimalFormat.format(costValue)}
        """.trimIndent()

        calculateAgainButton.setOnClickListener {
            val backIntent = Intent(this, InputActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }
}