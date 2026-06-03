package com.dperic.carcare.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dperic.carcare.components.BackToDashboardButton
import com.dperic.carcare.components.CarCareButton
import com.dperic.carcare.components.CarCareCard
import com.dperic.carcare.components.CarCareScreen
import com.dperic.carcare.viewmodel.CarCareViewModel

@Composable
fun VehiclesScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    CarCareScreen {
        Text(
            text = "Moja vozila",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Pregled spremljenih vozila")

        Spacer(modifier = Modifier.height(16.dp))

        CarCareButton(
            text = "+ Dodaj vozilo",
            onClick = {
                navController.navigate("add_vehicle")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.vehicles.isEmpty()) {
            CarCareCard(
                title = "Nema vozila",
                value = "Dodajte prvo vozilo u aplikaciju."
            )
        } else {
            viewModel.vehicles.forEach { vehicle ->
                CarCareCard(
                    title = "${vehicle.brand} ${vehicle.model}",
                    value = "Godina: ${vehicle.year}\nKilometraža: ${vehicle.mileage} km"
                )
            }
        }

        BackToDashboardButton(navController)
    }
}
