package com.example.emissions_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emissions_calculator.ui.theme.EmissionsCalculatorTheme
import com.example.emissions_calculator.viewmodel.EmissionsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmissionsCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmissionsCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmissionsCalculatorScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Калькулятор емісій") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EmissionsCalculatorTask()
        }
    }
}

@Composable
fun EmissionsCalculatorTask(
    viewModel: EmissionsViewModel = viewModel()
) {
    val inputData by viewModel.inputData.collectAsState()
    val calculationResult by viewModel.calculationResult.collectAsState()
    
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Заголовок
        Text(
            text = "Розрахунок емісій від різних видів палива",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        
        // Вугілля
        CoalCard(
            inputData = inputData,
            onBCoalChange = viewModel::updateBCoal,
            onQCoalChange = viewModel::updateQCoal,
            onKCoalChange = viewModel::updateKCoal
        )
        
        // Мазут
        OilCard(
            inputData = inputData,
            onBOilChange = viewModel::updateBOil,
            onQOilChange = viewModel::updateQOil,
            onKOilChange = viewModel::updateKOil
        )
        
        // Природний газ
        GasCard(
            inputData = inputData,
            onBGasChange = viewModel::updateBGas,
            onQGasChange = viewModel::updateQGas,
            onKGasChange = viewModel::updateKGas
        )
        
        // Кнопки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.calculate() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Обрахувати емісії")
            }
            
            OutlinedButton(
                onClick = { viewModel.clear() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистити")
            }
        }
        
        OutlinedButton(
            onClick = { viewModel.resetToDefaults() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Встановити значення за замовчуванням")
        }
        
        // Результат
        if (calculationResult.isSuccess) {
            ResultCard(result = calculationResult.result)
        } else if (calculationResult.error != null) {
            ErrorCard(error = calculationResult.error!!)
        }
    }
}

@Composable
fun CoalCard(
    inputData: com.example.emissions_calculator.data.EmissionsInputData,
    onBCoalChange: (String) -> Unit,
    onQCoalChange: (String) -> Unit,
    onKCoalChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🪨",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Вугілля",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Divider()
            
            OutlinedTextField(
                value = inputData.bCoal,
                onValueChange = onBCoalChange,
                label = { Text("Обс'яг палива (т)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = inputData.qCoal,
                onValueChange = onQCoalChange,
                label = { Text("Нижча теплота згорання (МДж/м³)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = inputData.kCoal,
                onValueChange = onKCoalChange,
                label = { Text("Масовий вміст золи в паливі (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

@Composable
fun OilCard(
    inputData: com.example.emissions_calculator.data.EmissionsInputData,
    onBOilChange: (String) -> Unit,
    onQOilChange: (String) -> Unit,
    onKOilChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🛢️",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Мазут",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Divider()
            
            OutlinedTextField(
                value = inputData.bOil,
                onValueChange = onBOilChange,
                label = { Text("Обс'яг палива (т)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = inputData.qOil,
                onValueChange = onQOilChange,
                label = { Text("Нижча теплота згорання (МДж/кг)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = inputData.kOil,
                onValueChange = onKOilChange,
                label = { Text("Масовий вміст золи в паливі (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

@Composable
fun GasCard(
    inputData: com.example.emissions_calculator.data.EmissionsInputData,
    onBGasChange: (String) -> Unit,
    onQGasChange: (String) -> Unit,
    onKGasChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "⛽",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Природний газ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Divider()
            
            OutlinedTextField(
                value = inputData.bGas,
                onValueChange = onBGasChange,
                label = { Text("Обс'яг палива (тис м³)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = inputData.qGas,
                onValueChange = onQGasChange,
                label = { Text("Об'ємна нижча теплота згорання (МДж/м³)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = inputData.kGas,
                onValueChange = onKGasChange,
                label = { Text("Густина (кг/м³)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

@Composable
fun ResultCard(result: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Результат розрахунків емісій",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun ErrorCard(error: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Text(
            text = error,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}
