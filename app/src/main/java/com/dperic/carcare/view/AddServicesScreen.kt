package com.dperic.carcare.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.dperic.carcare.viewmodel.CarCareViewModel

@Composable
fun AddServiceScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    Text(text = "Dodaj servis ekran")
}