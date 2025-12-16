package com.example.pw05tb_23_

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pw05tb_23_.ui.theme.PW05TB23_ХворостянийАртемКириловичTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW05TB23_ХворостянийАртемКириловичTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReliabilityCalculator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Калькулятор для порівняння надійності одноколової та двоколової систем електропередачі
 * та розрахунку збитків від перерв електропостачання
 * 
 * Завдання згідно з Прикладами 3.1 та 3.2
 */
@Composable
fun ReliabilityCalculator(modifier: Modifier = Modifier) {
    // Вхідні параметри
    var connections by remember { mutableStateOf("6") }
    var accidentPrice by remember { mutableStateOf("23.6") }
    var planedPrice by remember { mutableStateOf("17.6") }
    
    // Результати
    var results by remember { mutableStateOf<ReliabilityResults?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Заголовок з покращеним дизайном
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Калькулятор надійності систем електропередачі",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Text(
                    text = "Лабораторна робота 5",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        Divider(thickness = 2.dp)

        // Вхідні поля з покращеним дизайном
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Вхідні параметри",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                OutlinedTextField(
                    value = connections,
                    onValueChange = { connections = it },
                    label = { Text("Кількість приєднань 10 кВ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { Text("Приклад: 6") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = accidentPrice,
                    onValueChange = { accidentPrice = it },
                    label = { Text("Збитки від аварійних перерв (грн/кВт·год)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { Text("Приклад: 23.6") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = planedPrice,
                    onValueChange = { planedPrice = it },
                    label = { Text("Збитки від планових перерв (грн/кВт·год)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { Text("Приклад: 17.6") },
                    singleLine = true
                )
            }
        }

        // Кнопки з покращеним дизайном
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    connections = "6"
                    accidentPrice = "23.6"
                    planedPrice = "17.6"
                    errorMessage = null
                    results = null
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Приклад")
            }

            Button(
                onClick = {
                    errorMessage = null
                    val conn = connections.toIntOrNull()
                    val accPrice = accidentPrice.toDoubleOrNull()
                    val plPrice = planedPrice.toDoubleOrNull()

                    when {
                        conn == null || conn <= 0 -> {
                            errorMessage = "Введіть коректну кількість приєднань"
                        }
                        accPrice == null || accPrice <= 0 -> {
                            errorMessage = "Введіть коректну ціну для аварійних перерв"
                        }
                        plPrice == null || plPrice <= 0 -> {
                            errorMessage = "Введіть коректну ціну для планових перерв"
                        }
                        else -> {
                            results = calculateReliability(conn, accPrice, plPrice)
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Обчислити", fontWeight = FontWeight.Bold)
            }
        }

        // Помилка
        errorMessage?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "⚠",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Результати
        results?.let { result ->
            ResultsCard(result)
        }
    }
}

/**
 * Дані результатів розрахунків
 */
data class ReliabilityResults(
    // Одноколова система
    val omegaOc: Double,
    val tV_oc: Double,
    val kA_oc: Double,
    val kP_oc: Double,
    
    // Двоколова система
    val omegaDk: Double,
    val omegaDc: Double,
    
    // Збитки від перерв електропостачання
    val mathWnedA: Double,
    val mathWnedP: Double,
    val mathLosses: Double
)

/**
 * Функція розрахунків надійності та збитків
 * 
 * Формули згідно з Прикладами 3.1 та 3.2
 */
fun calculateReliability(
    connections: Int,
    accidentPrice: Double,
    planedPrice: Double
): ReliabilityResults {
    // Константи згідно з Прикладом 3.1
    val hoursPerYear = 8760.0

    val omegaOc = 0.01 + 0.07 + 0.015 + 0.02 + 0.03 * connections

    val tV_oc = (0.01 * 30 + 0.07 * 10 + 0.015 * 100 + 0.02 * 15 + (0.03 * connections) * 2) / omegaOc

    // Коефіцієнт аварійного простою одноколової системи
    val kA_oc = (omegaOc * tV_oc) / hoursPerYear
    
    // Коефіцієнт планового простою одноколової системи
    val kP_oc = 1.2 * (43.0 / hoursPerYear)

    // Частота відмов одночасно двох кіл двоколової системи
    val omegaDk = 2 * omegaOc * (kA_oc + kP_oc)
    
    // Частота відмов двоколової системи з урахуванням секційного вимикача
    val omegaDc = omegaDk + 0.02

    // Константи згідно з Прикладом 3.2
    val omega = 0.01
    val tV = 45e-3
    val Pm = 5.12e3
    val Tm = 6451.0
    val kP = 4e-3
    
    // Математичне сподівання аварійного недовідпущення електроенергії
    val mathWnedA = omega * tV * Pm * Tm
    
    // Математичне сподівання планового недовідпущення електроенергії
    val mathWnedP = kP * Pm * Tm
    
    // Математичне сподівання збитків від перерв електропостачання
    val mathLosses = accidentPrice * mathWnedA + planedPrice * mathWnedP
    
    return ReliabilityResults(
        omegaOc = omegaOc,
        tV_oc = tV_oc,
        kA_oc = kA_oc,
        kP_oc = kP_oc,
        omegaDk = omegaDk,
        omegaDc = omegaDc,
        mathWnedA = mathWnedA,
        mathWnedP = mathWnedP,
        mathLosses = mathLosses
    )
}

/**
 * Візуалізація результатів розрахунків
 */
@Composable
fun ResultsCard(results: ReliabilityResults) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Заголовок результатів
        Text(
            text = "Результати розрахунків",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // ========== ОДНОКОЛОВА СИСТЕМА ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "1. Одноколова система",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                ResultRow(
                    label = "Частота відмов ω_oc",
                    value = results.omegaOc,
                    unit = "рік⁻¹",
                    precision = 3
                )
                
                ResultRow(
                    label = "Середня тривалість відновлення t_в.ос",
                    value = results.tV_oc,
                    unit = "год",
                    precision = 1
                )
                
                ResultRow(
                    label = "Коефіцієнт аварійного простою k_a.oc",
                    value = results.kA_oc,
                    unit = "",
                    precision = 5,
                    scientific = true
                )
                
                ResultRow(
                    label = "Коефіцієнт планового простою k_п.ос",
                    value = results.kP_oc,
                    unit = "",
                    precision = 5,
                    scientific = true
                )
            }
        }

        // ========== ДВОКОЛОВА СИСТЕМА ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "2. Двоколова система",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                ResultRow(
                    label = "Частота відмов одночасно двох кіл ω_дк",
                    value = results.omegaDk,
                    unit = "рік⁻¹",
                    precision = 5,
                    scientific = true
                )
                
                ResultRow(
                    label = "Частота відмов з урахуванням секційного вимикача ω_дс",
                    value = results.omegaDc,
                    unit = "рік⁻¹",
                    precision = 4
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Divider(thickness = 1.dp)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = "💡 Висновок: Надійність двоколової системи є значно вищою ніж одноколової",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // ========== ЗБИТКИ ВІД ПЕРЕРВ ЕЛЕКТРОПОСТАЧАННЯ ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "3. Збитки від перерв електропостачання",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                ResultRow(
                    label = "Математичне сподівання аварійного недовідпущення M(W_нед.а)",
                    value = results.mathWnedA,
                    unit = "кВт·год",
                    precision = 0
                )
                
                ResultRow(
                    label = "Математичне сподівання планового недовідпущення M(W_нед.п)",
                    value = results.mathWnedP,
                    unit = "кВт·год",
                    precision = 0
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                
                ResultRow(
                    label = "Математичне сподівання збитків M(З_пер)",
                    value = results.mathLosses,
                    unit = "грн",
                    precision = 2
                )
            }
        }
    }
}

/**
 * Рядок з результатом
 */
@Composable
fun ResultRow(
    label: String,
    value: Double,
    unit: String,
    precision: Int,
    scientific: Boolean = false
) {
    val formattedValue = if (scientific && value < 0.001) {
        String.format("%.${precision}e", value)
    } else {
        String.format("%.${precision}f", value)
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$formattedValue $unit",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReliabilityCalculatorPreview() {
    PW05TB23_ХворостянийАртемКириловичTheme {
        ReliabilityCalculator()
    }
}
