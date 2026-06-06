package com.dperic.carcare.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dperic.carcare.view.AddServiceScreen
import com.dperic.carcare.view.AddVehicleScreen
import com.dperic.carcare.view.DashboardScreen
import com.dperic.carcare.view.RemindersScreen
import com.dperic.carcare.view.ServiceMapScreen
import com.dperic.carcare.view.ServicesScreen
import com.dperic.carcare.view.VehiclesScreen
import com.dperic.carcare.view.WeatherScreen
import com.dperic.carcare.viewmodel.CarCareViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: CarCareViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable("dashboard") {
            DashboardScreen(navController, viewModel)
        }

        composable("vehicles") {
            VehiclesScreen(navController, viewModel)
        }

        composable("add_vehicle") {
            AddVehicleScreen(navController, viewModel)
        }

        composable("services") {
            ServicesScreen(navController, viewModel)
        }

        composable("add_service") {
            AddServiceScreen(navController, viewModel)
        }

        composable("reminders") {
            RemindersScreen(navController, viewModel)
        }

        composable("service_map") {
            ServiceMapScreen(navController)
        }

        composable("weather") {
            WeatherScreen(navController, viewModel)
        }
    }
}

