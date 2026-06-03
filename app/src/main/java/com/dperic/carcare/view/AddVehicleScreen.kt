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
fun AddVehicleScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    CarCareScreen {
        Text(
            text = "Dodaj vozilo",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Unesite osnovne podatke o vozilu")

        Spacer(modifier = Modifier.height(16.dp))

        CarCareInputField(
            value = brand,
            label = "Marka",
            onValueChange = { brand = it }
        )

        CarCareInputField(
            value = model,
            label = "Model",
            onValueChange = { model = it }
        )

        CarCareInputField(
            value = year,
            label = "Godina",
            onValueChange = { year = it }
        )

        CarCareInputField(
            value = mileage,
            label = "Kilometraža",
            onValueChange = { mileage = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CarCareButton(
            text = "Spremi vozilo",
            onClick = {
                if (
                    brand.isBlank() ||
                    model.isBlank() ||
                    year.isBlank() ||
                    mileage.isBlank()
                ) {
                    errorMessage = "Sva polja moraju biti popunjena."
                } else {
                    viewModel.addVehicle(
                        brand = brand,
                        model = model,
                        year = year,
                        mileage = mileage
                    )

                    navController.popBackStack()
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