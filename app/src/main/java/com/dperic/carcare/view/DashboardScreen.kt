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
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import kotlin.math.sqrt


@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: CarCareViewModel
) {
    val vehicleCount = viewModel.vehicles.size
    val serviceCount = viewModel.serviceRecords.size
    val totalCost = viewModel.getTotalServiceCost()
    val nextReminder = viewModel.reminders.firstOrNull()?.title ?: "Nema podsjetnika"

    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val shakeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var lastShakeTime by remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = sqrt(x * x + y * y + z * z)
                val currentTime = System.currentTimeMillis()

                if (acceleration > 18f && currentTime - lastShakeTime > 1000) {
                    lastShakeTime = currentTime

                    scope.launch {
                        shakeOffset.animateTo(35f, animationSpec = tween(80))
                        shakeOffset.animateTo(-35f, animationSpec = tween(80))
                        shakeOffset.animateTo(20f, animationSpec = tween(80))
                        shakeOffset.animateTo(0f, animationSpec = tween(80))
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Nije potrebno za ovu funkcionalnost
            }
        }

        if (accelerometer != null) {
            sensorManager.registerListener(
                sensorListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }


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
            fontSize = 64.sp,
            modifier = Modifier.graphicsLayer {
                translationX = shakeOffset.value
            }
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