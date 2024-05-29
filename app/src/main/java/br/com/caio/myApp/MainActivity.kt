package br.com.caio.myApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.caio.myApp.ui.theme.MyApplicationTheme
import br.com.caio.myApp.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                var currentScreen by remember { mutableStateOf(Screen.Main) }

                when (currentScreen) {
                    Screen.Main -> MainScreen(
                        onNavigateToSecondScreen = { currentScreen = Screen.Second }
                    )
                    Screen.Second -> SecondScreen(
                        onNavigateBack = { currentScreen = Screen.Main }
                    )
                }
            }
        }
    }
}

enum class Screen {
    Main,
    Second
}

@Composable
fun MainScreen(onNavigateToSecondScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Receba as melhores dicas de dietas e exercícios de acordo com o seu perfil.",
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White
            )
            Button(
                onClick = onNavigateToSecondScreen,
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Começar")
            }
        }
    }
}

@Composable
fun SecondScreen(onNavigateBack: () -> Unit) {
    var altura by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Informe os dados abaixo:", color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de entrada para altura
            OutlinedTextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Altura (cm)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Campo de entrada para peso
            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Botão para calcular IMC
            Button(onClick = {
                // Aqui você pode calcular o IMC utilizando os valores de altura e peso
                val alturaFloat = altura.toFloatOrNull() ?: 0f
                val pesoFloat = peso.toFloatOrNull() ?: 0f
                val imc = pesoFloat / (alturaFloat / 100 * alturaFloat / 100)
            }) {
                Text(text = "Calcular")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para voltar à tela anterior
            Button(onClick = onNavigateBack) {
                Text(text = "Voltar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen(onNavigateToSecondScreen = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    MyApplicationTheme {
        SecondScreen(onNavigateBack = {})
    }
}