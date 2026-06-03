package com.dperic.carcare.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.dperic.carcare.viewmodel.CarCareViewModel

@Composable
fun VehiclesScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    Text(text = "Moja vozila ekran")
}

