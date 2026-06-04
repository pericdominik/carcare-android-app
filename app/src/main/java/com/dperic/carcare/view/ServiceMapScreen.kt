package com.dperic.carcare.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dperic.carcare.components.BackToDashboardButton
import com.dperic.carcare.components.CarCareCard
import com.dperic.carcare.components.CarCareScreen

@Composable
fun ServiceMapScreen(
    navController: NavController
) {
    CarCareScreen {
        Text(
            text = "Mapa servisa",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Pregled servisnih lokacija u blizini")

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    color = Color(0xFFDADADA),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Prikaz mape servisa",
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CarCareCard(
            title = "AutoZubak servis",
            value = "Lokacija: Osijek\nUsluge: mali servis, kočnice, dijagnostika"
        )

        CarCareCard(
            title = "Auto Servis Rudec",
            value = "Lokacija: Briješće\nUsluge: gume, optika trapa, servis klime"
        )

        CarCareCard(
            title = "Brzi servis Novak",
            value = "Lokacija: Višnjevac\nUsluge: zamjena ulja, filteri, akumulatori"
        )

        BackToDashboardButton(navController)
    }
}