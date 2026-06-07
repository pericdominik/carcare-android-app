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
import androidx.compose.ui.platform.LocalContext
import com.dperic.carcare.notification.ReminderNotificationScheduler

@Composable
fun RemindersScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var selectedVehicleId by remember {
        mutableStateOf(viewModel.vehicles.firstOrNull()?.id ?: "")
    }

    val context = LocalContext.current

    CarCareScreen {
        Text(
            text = "Podsjetnici",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Servisni i registracijski podsjetnici")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Odaberite vozilo",
            fontWeight = FontWeight.Bold
        )

        if (viewModel.vehicles.isEmpty()) {
            CarCareCard(
                title = "Nema vozila",
                value = "Prvo dodajte vozilo kako biste mogli dodati podsjetnik."
            )
        } else {
            viewModel.vehicles.forEach { vehicle ->
                val vehicleName = "${vehicle.brand} ${vehicle.model}"
                val buttonText = if (vehicle.id == selectedVehicleId) {
                    "x $vehicleName"
                } else {
                    vehicleName
                }

                CarCareButton(
                    text = buttonText,
                    onClick = {
                        selectedVehicleId = vehicle.id
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CarCareInputField(
            value = title,
            label = "Naziv podsjetnika",
            onValueChange = { title = it }
        )

        CarCareInputField(
            value = date,
            label = "Datum, npr. 07.07.2026.",
            onValueChange = { date = it }
        )

        CarCareInputField(
            value = time,
            label = "Vrijeme, npr. 14:30",
            onValueChange = { time = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CarCareButton(
            text = "Dodaj podsjetnik",
            onClick = {
                if (selectedVehicleId.isBlank()) {
                    errorMessage = "Prvo morate odabrati vozilo."
                } else if (title.isBlank() || date.isBlank() || time.isBlank()) {
                    errorMessage = "Naziv, datum i vrijeme podsjetnika moraju biti popunjeni."
                } else {
                    viewModel.addReminder(selectedVehicleId, title, date, time)

                    ReminderNotificationScheduler.scheduleReminder(
                        context = context,
                        title = title,
                        date = date,
                        time = time
                    )

                    title = ""
                    date = ""
                    time = ""
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
                    value = "Vozilo: ${viewModel.getVehicleName(reminder.vehicleId)}\nRok: ${reminder.date}\nVrijeme: ${reminder.time}\nStatus: $status"
                )

                if (!reminder.isDone) {
                    CarCareButton(
                        text = "Označi kao završeno",
                        onClick = {
                            viewModel.completeReminder(reminder.id)
                        }
                    )
                }

                CarCareButton(
                    text = "Obriši podsjetnik",
                    onClick = {
                        viewModel.deleteReminder(reminder.id)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        BackToDashboardButton(navController)
    }
}