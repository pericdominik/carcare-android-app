package com.dperic.carcare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

val CarCareBlue = Color(0xFF4564A0)
val CarCareBackground = Color(0xFFF4F6FA)
val CarCareCardColor = Color(0xFFFFFFFF)
val CarCareTextDark = Color(0xFF1E1E1E)

@Composable
fun CarCareScreen(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CarCareBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun CarCareCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CarCareCardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = CarCareTextDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = CarCareTextDark
            )
        }
    }
}

@Composable
fun CarCareButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(48.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CarCareBlue,
            contentColor = Color.White
        )
    ) {
        Text(text = text,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CarCareInputField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CarCareBlue,
            focusedLabelColor = CarCareBlue,
            cursorColor = CarCareBlue
        )
    )
}

@Composable
fun BackToDashboardButton(
    navController: NavController
) {
    Spacer(modifier = Modifier.height(16.dp))

    TextButton(
        onClick = {
            navController.navigate("dashboard") {
                popUpTo("dashboard") {
                    inclusive = false
                }
            }
        }
    ) {
        Text(
            text = "Natrag na početnu",
            color = CarCareBlue,
            fontWeight = FontWeight.SemiBold
        )
    }
}

