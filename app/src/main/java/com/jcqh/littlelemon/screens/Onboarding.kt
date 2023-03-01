package com.jcqh.littlelemon

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jcqh.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Onboarding(navController: NavHostController, saveUserData: (UserData) -> Unit) {
    val context = LocalContext.current

    Column {
        TopAppBar()
        var firstName by remember {
            mutableStateOf("")
        }
        var lastName by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }

        Column{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LittleLemonColor.green),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.greeting),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .padding(40.dp))
            }
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.personal_information),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
                )
                OutlinedTextField(
                    value = firstName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    onValueChange = {
                        firstName = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.first_name))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = LittleLemonColor.green,
                        focusedLabelColor = LittleLemonColor.green,
                        cursorColor = LittleLemonColor.green,
                        backgroundColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = lastName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    onValueChange = {
                        lastName = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.last_name))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = LittleLemonColor.green,
                        focusedLabelColor = LittleLemonColor.green,
                        cursorColor = LittleLemonColor.green,
                        backgroundColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    onValueChange = {
                        email = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = LittleLemonColor.green,
                        focusedLabelColor = LittleLemonColor.green,
                        cursorColor = LittleLemonColor.green,
                        backgroundColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Button(
                    onClick = {
                        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                            Toast.makeText(context, context.getString(R.string.registration_unsuccessful), Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(context, context.getString(R.string.registration_successful), Toast.LENGTH_LONG).show()
                            val userData = UserData(firstName, lastName, email)
                            saveUserData(userData)
                            navController.navigate(Home.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 90.dp)
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
            }
        }
    }
}