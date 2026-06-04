package com.dperic.carcare.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dperic.carcare.components.BackToDashboardButton
import com.dperic.carcare.components.CarCareButton
import com.dperic.carcare.components.CarCareCard
import com.dperic.carcare.components.CarCareInputField
import com.dperic.carcare.components.CarCareScreen

@Composable
fun WeatherScreen(
    navController: NavController
) {
    var city by remember { mutableStateOf("") }
    var weatherResult by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    CarCareScreen {
        Text(
            text = "Vremenski uvjeti",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Provjera uvjeta za vožnju")

        Spacer(modifier = Modifier.height(16.dp))

        CarCareInputField(
            value = city,
            label = "Unesite grad",
            onValueChange = { city = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CarCareButton(
            text = "Provjeri vrijeme",
            onClick = {
                if (city.isBlank()) {
                    errorMessage = "Unesite naziv grada."
                    weatherResult = ""
                } else {
                    errorMessage = ""
                    weatherResult =
                        "Grad: $city\nTemperatura: 22°C\nVrijeme: Sunčano\nUvjeti za vožnju: Dobri"
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotBlank()) {
            CarCareCard(
                title = "Greška",
                value = errorMessage
            )
        }

        if (weatherResult.isNotBlank()) {
            CarCareCard(
                title = "Rezultat",
                value = weatherResult
            )
        } else {
            CarCareCard(
                title = "Primjer prikaza",
                value = "Unesite grad kako biste provjerili vremenske uvjete za vožnju."
            )
        }

        BackToDashboardButton(navController)
    }
}