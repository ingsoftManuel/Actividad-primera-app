package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                DatosBasicosScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosBasicosScreen() {
    // Estado de los campos de texto
    var nombre by remember { mutableStateOf("") }
    var anioNacimiento by remember { mutableStateOf("") }
    var anioActual by remember { mutableStateOf("") }

    // Estado de lo que se muestra al presionar el botón
    var nombreAMostrar by remember { mutableStateOf("") }
    var nacimientoAMostrar by remember { mutableStateOf("") }
    var actualAMostrar by remember { mutableStateOf("") }
    var edadAMostrar by remember { mutableStateOf("") }

    // Snackbar y corrutinas
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("DatosBásicos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val nac = anioNacimiento.toIntOrNull()
                    val act = anioActual.toIntOrNull()

                    if (nombre.isBlank() || nac == null || act == null) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Completa todos los campos con valores válidos"
                            )
                        }
                    } else {
                        val edad = act - nac
                        nombreAMostrar = nombre
                        nacimientoAMostrar = nac.toString()
                        actualAMostrar = act.toString()
                        edadAMostrar = edad.toString()

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Datos calculados correctamente"
                            )
                        }
                    }
                },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = anioNacimiento,
                onValueChange = { anioNacimiento = it },
                label = { Text("Año de nacimiento") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = anioActual,
                onValueChange = { anioActual = it },
                label = { Text("Año actual") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            if (edadAMostrar.isNotEmpty()) {
                Text("Nombre: $nombreAMostrar  Año de nacimiento: $nacimientoAMostrar  Año actual: $actualAMostrar  Edad: $edadAMostrar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DatosBasicosPreview() {
    MyApplicationTheme {
        DatosBasicosScreen()
    }
}