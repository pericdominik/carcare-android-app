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
fun AddServiceScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    var type by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val selectedVehicleId = viewModel.vehicles.firstOrNull()?.id ?: ""

    CarCareScreen {
        Text(
            text = "Dodaj servis",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Unesite podatke o servisnom zapisu")

        Spacer(modifier = Modifier.height(16.dp))

        CarCareInputField(
            value = type,
            label = "Tip servisa",
            onValueChange = { type = it }
        )

        CarCareInputField(
            value = date,
            label = "Datum",
            onValueChange = { date = it }
        )

        CarCareInputField(
            value = mileage,
            label = "Kilometraža",
            onValueChange = { mileage = it }
        )

        CarCareInputField(
            value = price,
            label = "Cijena",
            onValueChange = { price = it }
        )

        CarCareInputField(
            value = note,
            label = "Napomena",
            onValueChange = { note = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CarCareButton(
            text = "Spremi servis",
            onClick = {
                if (
                    type.isBlank() ||
                    date.isBlank() ||
                    mileage.isBlank() ||
                    price.isBlank()
                ) {
                    errorMessage = "Tip servisa, datum, kilometraža i cijena moraju biti popunjeni."
                } else {
                    viewModel.addServiceRecord(
                        vehicleId = selectedVehicleId,
                        type = type,
                        date = date,
                        mileage = mileage,
                        price = price,
                        note = note
                    )

                    navController.navigate("services") {
                        popUpTo("services") {
                            inclusive = true
                        }
                    }
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

        BackToDashboardButton(navController)
    }
}