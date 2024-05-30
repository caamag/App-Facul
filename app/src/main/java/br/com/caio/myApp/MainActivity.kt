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
                        onNavigateBack = { currentScreen = Screen.Main },
                        onNavigateToResultScreen = { imc ->
                            currentScreen = when {
                                imc < 18 -> Screen.Underweight
                                imc < 24 -> Screen.NormalWeight
                                else -> Screen.Overweight
                            }
                        }
                    )
                    Screen.Underweight -> UnderweightScreen(onNavigateBack = { currentScreen = Screen.Second })
                    Screen.NormalWeight -> NormalWeightScreen(onNavigateBack = { currentScreen = Screen.Second })
                    Screen.Overweight -> OverweightScreen(onNavigateBack = { currentScreen = Screen.Second })
                }
            }
        }
    }
}

enum class Screen {
    Main,
    Second,
    Underweight,
    NormalWeight,
    Overweight,
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
fun SecondScreen(onNavigateBack: () -> Unit, onNavigateToResultScreen: (Float) -> Unit) {
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

            OutlinedTextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Altura (cm)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(onClick = {
                val alturaFloat = altura.toFloatOrNull() ?: 0f
                val pesoFloat = peso.toFloatOrNull() ?: 0f
                if (alturaFloat > 0 && pesoFloat > 0) {
                    val imc = pesoFloat / (alturaFloat / 100 * alturaFloat / 100)
                    onNavigateToResultScreen(imc)
                }
            }) {
                Text(text = "Calcular")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateBack) {
                Text(text = "Voltar")
            }
        }
    }
}

@Composable
fun UnderweightScreen(onNavigateBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Você está abaixo do peso",
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pode-se incluir na dieta alimentos como: ovos, leite e derivados, " +
                        "grãos integrais, feijão e vegetais ricos em amido como a batata. " +
                        "Todos esses alimentos, na quantidade correta e preparados de maneira " +
                        "saudável, aumentam o aporte calórico e levam ao ganho de peso seguro.",
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Exercícios recomendados são pilates, caminhada, ciclismo, musculação," +
                "natação, yoga e Tai Chi. Controlando uma boa dieta e exercícios frequentes você terá" +
                "resultados visíveis em breve.",
                color = Color.Black
            )
            Button(onClick = onNavigateBack) {
                Text(text = "Voltar")
            }
        }
    }
}

@Composable
fun NormalWeightScreen(onNavigateBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Meus parabéns, você está com o peso ideal.", color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Consuma uma variedade de alimentos saudáveis, incluindo frutas, legumes, " +
                        "proteínas magras, grãos integrais e gorduras saudáveis. Evite alimentos " +
                        "processados, ricos em açúcar e gorduras saturadas. Mantenha-se hidratado, " +
                        "bebendo água em quantidade suficiente”, diz Angelica",
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mesmo estando com o peso ideal, molesa não faz parte do jogo. Uma rotina de exercícios" +
                        "ainda se faz necessário para mantes um vida saudável. Portando praticar esportes, ir a academia " +
                        "e se relacionar com a natureza são fatores determinantes até mesmo para um corpo que está dentro " +
                        "do resultado esperado."
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateBack) {
                Text(text = "Voltar")
            }
        }
    }
}

@Composable
fun OverweightScreen(onNavigateBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Você está acima do peso.", color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Usar alimentos assados, cozidos ou grelhados. Evitar frituras e retirar peles " +
                        "e couros. As carnes, o leite, o queijo e o iogurte devem ser magros. Beber " +
                        "bastante água durante o dia ( cerca de 8 copos por dia)"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Para você os exercícios são vitais. Uma rotina de cárdio será a sua maior aliada." +
                        "Correr na esteira, fazer caminhas e andar de bicicleta são práticas simples que fazem toda a diferença." +
                        "O sobrepeso pode causar baixa autestima, mas não desista! Sempre há uma nova chanve para estar em boa forma."
            )
            Spacer(modifier = Modifier.height(16.dp))

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
        SecondScreen(onNavigateBack = {}, onNavigateToResultScreen = {})
    }
}

@Preview(showBackground = true)
@Composable
fun UnderweightScreenPreview() {
    MyApplicationTheme {
        UnderweightScreen(onNavigateBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun NormalWeightScreenPreview() {
    MyApplicationTheme {
        NormalWeightScreen(onNavigateBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun OverweightScreenPreview() {
    MyApplicationTheme {
        OverweightScreen(onNavigateBack = {})
    }
}



