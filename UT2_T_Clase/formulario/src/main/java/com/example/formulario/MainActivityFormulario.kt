package com.example.formulario

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import com.example.formulario.ui.theme.ProyectoClaseTTheme

class MainActivityFormulario : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaRegistro()
        }
    }
}



@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun PantallaRegistroPreview() {
    PantallaRegistro()
}


@Composable
private fun PantallaRegistro() {
    ProyectoClaseTTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FormularioRegistro( Modifier.padding(innerPadding))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistro(modificador: Modifier = Modifier){

    var mensaje by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("")}
    var numero by remember { mutableStateOf("")}
    var edad by remember { mutableStateOf("")}
    var acepta by remember { mutableStateOf(false)}
    var opciones = listOf("Presencial", "Online", "Mixta")
    var modalidad by remember { mutableStateOf("")}
    var errorNombre = nombre.isBlank()
    var errorEmail = !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    var errorPasswrd = password.length < 6
    var formValido = !errorNombre && !errorPasswrd && !errorEmail && acepta
    var intentoEnvio by remember { mutableStateOf(false)}

    var curso by remember { mutableStateOf("") }
    var expandido by remember { mutableStateOf(false) }

    val cursos = listOf("1º DAM", "2º DAM")

    Box(modifier = modificador.fillMaxSize()){
        Image(
            painter = painterResource(R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Registro Erik",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = errorNombre && intentoEnvio ,
                supportingText = {
                    if (intentoEnvio && errorNombre) Text("Nombre Obligatorio")
                }
            )

            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellidos") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                isError = errorEmail && intentoEnvio,
                supportingText = {
                    if (intentoEnvio && errorEmail) Text("Correo Incorrecto")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = numero,
                onValueChange = { numero = it },
                label = { Text("Telefono") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = errorPasswrd && intentoEnvio,
                supportingText = {
                    if (intentoEnvio && errorPasswrd) Text("Debe ser mas larga")
                },
                visualTransformation = PasswordVisualTransformation(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )

            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = acepta,
                    onCheckedChange = { acepta = it }
                )
                Text("Aceptas los terminos.")
            }
            Text("Modalidad:")
            for (opcion in opciones) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = opcion == modalidad,
                        onClick = { modalidad = opcion }
                    )
                    Text(opcion)
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = it }
            ) {
                OutlinedTextField(
                    value = curso,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Curso") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                    },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    for (opcion in cursos) {
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                curso = opcion
                                expandido = false
                            }
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    Alignment.CenterHorizontally
                ), modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        mensaje = "Buenas, $nombre $apellido, eres guay."
                        intentoEnvio = true
                    },
                    modifier = Modifier.width(140.dp)
                ) {
                    Text("Registrarse")
                }

                Button(
                    onClick = {
                        mensaje = ""
                        nombre = ""
                        apellido = ""
                        edad = ""
                        email = ""
                        numero = ""
                        password = ""
                        acepta = false
                        modalidad = ""
                        intentoEnvio = false
                    },

                    modifier = Modifier.width(140.dp)
                ) {
                    Text("Reiniciar")
                }

            }

            if (formValido && intentoEnvio) {
                Text(mensaje)
            }


        }
    }

}

