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
import com.dperic.carcare.viewmodel.CarCareViewModel

@Composable
fun RemindersScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val selectedVehicleId = viewModel.vehicles.firstOrNull()?.id ?: ""

    CarCareScreen {
        Text(
            text = "Podsjetnici",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Servisni i registracijski podsjetnici")

        Spacer(modifier = Modifier.height(16.dp))

        CarCareInputField(
            value = title,
            label = "Naziv podsjetnika",
            onValueChange = { title = it }
        )

        CarCareInputField(
            value = date,
            label = "Datum ili opis roka",
            onValueChange = { date = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CarCareButton(
            text = "Dodaj podsjetnik",
            onClick = {
                if (title.isBlank() || date.isBlank()) {
                    errorMessage = "Naziv i datum podsjetnika moraju biti popunjeni."
                } else {
                    viewModel.addReminder(
                        vehicleId = selectedVehicleId,
                        title = title,
                        date = date
                    )

                    title = ""
                    date = ""
                    errorMessage = ""
                }
            }
        )

        if (errorMessage.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))

            CarCareCard(
                title = "Greška",
                value = errorMessage
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.reminders.isEmpty()) {
            CarCareCard(
                title = "Nema podsjetnika",
                value = "Dodajte prvi podsjetnik za servis ili registraciju."
            )
        } else {
            viewModel.reminders.forEach { reminder ->
                val status = if (reminder.isDone) "Završeno" else "Aktivno"

                CarCareCard(
                    title = reminder.title,
                    value = "Rok: ${reminder.date}\nStatus: $status"
                )
            }
        }

        BackToDashboardButton(navController)
    }
}