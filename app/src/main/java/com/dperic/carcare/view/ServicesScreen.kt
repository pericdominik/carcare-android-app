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
fun ServicesScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    CarCareScreen {
        Text(
            text = "Servisi",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Pregled servisnih zapisa")

        Spacer(modifier = Modifier.height(16.dp))

        CarCareButton(
            text = "+ Dodaj servis",
            onClick = {
                navController.navigate("add_service")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.serviceRecords.isEmpty()) {
            CarCareCard(
                title = "Nema servisa",
                value = "Dodajte prvi servisni zapis."
            )
        } else {
            viewModel.serviceRecords.forEach { service ->
                CarCareCard(
                    title = service.type,
                    value = "Datum: ${service.date}\nKilometraža: ${service.mileage} km\nCijena: ${service.price} €\nNapomena: ${service.note}"
                )
            }
        }

        BackToDashboardButton(navController)
    }
}