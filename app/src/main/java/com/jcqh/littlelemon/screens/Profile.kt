package com.jcqh.littlelemon.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jcqh.littlelemon.MainActivity
import com.jcqh.littlelemon.Onboarding
import com.jcqh.littlelemon.R
import com.jcqh.littlelemon.TopAppBar
import com.jcqh.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Profile(navController: NavHostController, getUserData: (String) -> String, clearUserData: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
    ) {
        TopAppBar()
        Text(
            text = stringResource(id = R.string.personal_information),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
        )
        Text(
            text = stringResource(id = R.string.first_name),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 20.dp)
        )
        Text(
            text = getUserData(MainActivity.FIRST_NAME),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = stringResource(id = R.string.last_name),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 20.dp)
        )
        Text(
            text = getUserData(MainActivity.LAST_NAME),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = stringResource(id = R.string.email),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 20.dp)
        )
        Text(
            text = getUserData(MainActivity.EMAIL),
            style = MaterialTheme.typography.body1
        )
        Button(
            onClick = {
                clearUserData()
                navController.navigate(Onboarding.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp)
        ) {
            Text(text = stringResource(id = R.string.logout))
        }
    }
}

