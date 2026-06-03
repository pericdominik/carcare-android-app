package com.dperic.carcare.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dperic.carcare.components.CarCareButton
import com.dperic.carcare.components.CarCareCard
import com.dperic.carcare.components.CarCareScreen
import com.dperic.carcare.viewmodel.CarCareViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    val vehicleCount = viewModel.vehicles.size
    val serviceCount = viewModel.serviceRecords.size
    val totalCost = viewModel.getTotalServiceCost()
    val nextReminder = viewModel.reminders.firstOrNull()?.title ?: "Nema podsjetnika"

    CarCareScreen {
        Text(
            text = "CarCare",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Pregled održavanja vozila",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "🚗",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        CarCareCard(
            title = "Broj vozila",
            value = vehicleCount.toString()
        )

        CarCareCard(
            title = "Ukupni troškovi",
            value = "$totalCost €"
        )

        CarCareCard(
            title = "Broj servisa",
            value = serviceCount.toString()
        )

        CarCareCard(
            title = "Najbliži podsjetnik",
            value = nextReminder
        )

        Spacer(modifier = Modifier.height(20.dp))

        CarCareButton(
            text = "Moja vozila",
            onClick = { navController.navigate("vehicles") }
        )

        CarCareButton(
            text = "Servisi",
            onClick = { navController.navigate("services") }
        )

        CarCareButton(
            text = "Podsjetnici",
            onClick = { navController.navigate("reminders") }
        )

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("service_map") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Mapa")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { navController.navigate("weather") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Vrijeme")
            }
        }
    }
}