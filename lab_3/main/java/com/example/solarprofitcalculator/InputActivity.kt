package com.example.solarprofitcalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InputActivity : AppCompatActivity() {

    private lateinit var powerValueInput: EditText
    private lateinit var sigmaValueInput: EditText
    private lateinit var costValueInput: EditText
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_activity)

        powerValueInput = findViewById(R.id.powerValueInput)
        sigmaValueInput = findViewById(R.id.sigmaValueInput)
        costValueInput = findViewById(R.id.costValueInput)
        calculateButton = findViewById(R.id.calculateButton)

        // Add focus listeners for better UX
        setupFocusListener(powerValueInput)
        setupFocusListener(sigmaValueInput)
        setupFocusListener(costValueInput)

        calculateButton.setOnClickListener {
            val powerValue = powerValueInput.text.toString().toDoubleOrNull() ?: 0.0
            val sigmaValue = sigmaValueInput.text.toString().toDoubleOrNull() ?: 0.0
            val costValue = costValueInput.text.toString().toDoubleOrNull() ?: 0.0

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("powerValue", powerValue)
                putExtra("sigmaValue", sigmaValue)
                putExtra("costValue", costValue)
            }

            startActivity(intent)
        }
    }

    private fun setupFocusListener(editText: EditText) {
        editText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.input_background_focused)
            } else {
                view.setBackgroundResource(R.drawable.input_background)
            }
        }
    }
}
