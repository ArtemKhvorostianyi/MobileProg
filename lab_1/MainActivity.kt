package com.example.lab_1

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab_1.ui.theme.Lab_1Theme
import com.example.lab_1.viewmodel.Task1ViewModel
import com.example.lab_1.viewmodel.Task2ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab_1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TasksScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Калькулятор палива") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Завдання 1
            TaskCard(title = "Завдання 1") {
                FuelCalculatorTask1()
            }
            
            // Завдання 2
            TaskCard(title = "Завдання 2") {
                FuelCalculatorTask2()
            }
        }
    }
}

@Composable
fun TaskCard(
    title: String,
    content: @Composable () -> Unit
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
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Divider()
            content()
        }
    }
}

@Composable
fun FuelCalculatorTask1(
    viewModel: Task1ViewModel = viewModel()
) {
    val inputData by viewModel.inputData.collectAsState()
    val calculationResult by viewModel.calculationResult.collectAsState()
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Поля вводу
        InputFieldsTask1(
            inputData = inputData,
            onHpChange = viewModel::updateHp,
            onCChange = viewModel::updateC,
            onSpChange = viewModel::updateSp,
            onNpChange = viewModel::updateNp,
            onOpChange = viewModel::updateOp,
            onApChange = viewModel::updateAp,
            onWChange = viewModel::updateW
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
                Text("Обрахувати")
            }
            
            OutlinedButton(
                onClick = { viewModel.clear() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистити")
            }
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
fun InputFieldsTask1(
    inputData: com.example.lab_1.data.Task1InputData,
    onHpChange: (String) -> Unit,
    onCChange: (String) -> Unit,
    onSpChange: (String) -> Unit,
    onNpChange: (String) -> Unit,
    onOpChange: (String) -> Unit,
    onApChange: (String) -> Unit,
    onWChange: (String) -> Unit
) {
    OutlinedTextField(
        value = inputData.hp,
        onValueChange = onHpChange,
        label = buildLabel("Hp"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.c,
        onValueChange = onCChange,
        label = buildLabel("C"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.sp,
        onValueChange = onSpChange,
        label = buildLabel("Sp"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.np,
        onValueChange = onNpChange,
        label = buildLabel("Np"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.op,
        onValueChange = onOpChange,
        label = buildLabel("Op"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.w,
        onValueChange = onWChange,
        label = buildLabel("W"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.ap,
        onValueChange = onApChange,
        label = buildLabel("Ap"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun FuelCalculatorTask2(
    viewModel: Task2ViewModel = viewModel()
) {
    val inputData by viewModel.inputData.collectAsState()
    val calculationResult by viewModel.calculationResult.collectAsState()
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Поля вводу
        InputFieldsTask2(
            inputData = inputData,
            onHgChange = viewModel::updateHg,
            onCgChange = viewModel::updateCg,
            onSgChange = viewModel::updateSg,
            onOgChange = viewModel::updateOg,
            onVgChange = viewModel::updateVg,
            onWgChange = viewModel::updateWg,
            onAgChange = viewModel::updateAg,
            onQdafChange = viewModel::updateQdaf
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
                Text("Обрахувати")
            }
            
            OutlinedButton(
                onClick = { viewModel.clear() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистити")
            }
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
fun InputFieldsTask2(
    inputData: com.example.lab_1.data.Task2InputData,
    onHgChange: (String) -> Unit,
    onCgChange: (String) -> Unit,
    onSgChange: (String) -> Unit,
    onOgChange: (String) -> Unit,
    onVgChange: (String) -> Unit,
    onWgChange: (String) -> Unit,
    onAgChange: (String) -> Unit,
    onQdafChange: (String) -> Unit
) {
    OutlinedTextField(
        value = inputData.hg,
        onValueChange = onHgChange,
        label = buildLabel("Hг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.cg,
        onValueChange = onCgChange,
        label = buildLabel("Cг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.sg,
        onValueChange = onSgChange,
        label = buildLabel("Sг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.og,
        onValueChange = onOgChange,
        label = buildLabel("Oг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.vg,
        onValueChange = onVgChange,
        label = buildLabel("Vг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.wg,
        onValueChange = onWgChange,
        label = buildLabel("Wг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.ag,
        onValueChange = onAgChange,
        label = buildLabel("Aг"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    
    OutlinedTextField(
        value = inputData.qdaf,
        onValueChange = onQdafChange,
        label = buildLabel("Q"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
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
                text = "Результат розрахунків",
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

@Composable
fun buildLabel(variableName: String): @Composable () -> Unit {
    return {
        Text(
            buildAnnotatedString {
                if (variableName.length == 1) {
                    append(variableName.uppercase())
                } else {
                    append(variableName[0].uppercase())
                    withStyle(
                        style = SpanStyle(
                            fontSize = 12.sp,
                            baselineShift = BaselineShift.Superscript
                        )
                    ) {
                        append(variableName.substring(1))
                    }
                }
                when {
                    variableName[0] == 'V' -> append(" (мг/кг)")
                    variableName[0] == 'A' && variableName.length > 1 && variableName[1] != 'p' -> append(" ")
                    variableName[0] == 'Q' -> append(" (МДж/кг)")
                    else -> append(" (%)")
                }
            }
        )
    }
}
