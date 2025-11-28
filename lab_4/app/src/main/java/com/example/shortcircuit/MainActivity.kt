package com.example.shortcircuit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var inputUcn: TextInputEditText
    private lateinit var inputSk: TextInputEditText
    private lateinit var inputUk: TextInputEditText
    private lateinit var inputSnomT: TextInputEditText
    private lateinit var btnCalculate: MaterialButton
    private lateinit var tvResult: android.widget.TextView
    private lateinit var cardResults: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize views
        inputUcn = findViewById(R.id.inputUcn)
        inputSk = findViewById(R.id.inputSk)
        inputUk = findViewById(R.id.inputUk)
        inputSnomT = findViewById(R.id.inputSnomT)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvResult = findViewById(R.id.tvResult)
        cardResults = findViewById(R.id.cardResults)

        // Set click listener
        btnCalculate.setOnClickListener {
            performCalculation()
        }
    }

    private fun performCalculation() {
        try {
            // Get input values
            val ucnText = inputUcn.text?.toString()?.trim()
            val skText = inputSk.text?.toString()?.trim()
            val ukText = inputUk.text?.toString()?.trim()
            val snomTText = inputSnomT.text?.toString()?.trim()

            // Validate inputs
            if (ucnText.isNullOrEmpty() || skText.isNullOrEmpty() || 
                ukText.isNullOrEmpty() || snomTText.isNullOrEmpty()) {
                showError("Будь ласка, заповніть всі поля!")
                return
            }

            val UcnVal = ucnText.toDouble()
            val SkVal = skText.toDouble()
            val UkVal = ukText.toDouble()
            val SnomTVal = snomTText.toDouble()

            // Validate positive values
            if (UcnVal <= 0 || SkVal <= 0 || UkVal <= 0 || SnomTVal <= 0) {
                showError("Всі значення повинні бути більше нуля!")
                return
            }

            // Perform calculations
            val Xc = (UcnVal.pow(2)) / SkVal
            val Xt = (UkVal * (UcnVal.pow(2))) / (100 * SnomTVal)
            val X_all = Xc + Xt
            val Ipo = UcnVal / (sqrt(3.0) * X_all)

            // Format results
            val resultText = """
                Опори елементів заступної схеми:
                
                Xc = ${"%.3f".format(Xc)} Ом
                Xt = ${"%.3f".format(Xt)} Ом
                
                Сумарний опір для точки К1:
                X_all = ${"%.3f".format(X_all)} Ом
                
                Початкове діюче значення струму трифазного КЗ:
                Ipo = ${"%.3f".format(Ipo)} кА
            """.trimIndent()

            // Display results
            tvResult.text = resultText
            cardResults.visibility = View.VISIBLE

            // Scroll to results
            cardResults.post {
                cardResults.requestFocus()
            }

        } catch (e: NumberFormatException) {
            showError("Помилка: перевірте правильність введених даних!")
        } catch (e: Exception) {
            showError("Помилка: ${e.message}")
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        cardResults.visibility = View.GONE
    }
}
