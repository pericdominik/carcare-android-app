package com.dperic.carcare.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dperic.carcare.components.BackToDashboardButton
import com.dperic.carcare.components.CarCareButton
import com.dperic.carcare.components.CarCareCard
import com.dperic.carcare.components.CarCareScreen

@Composable
fun ServiceMapScreen(
    navController: NavController
) {
    val context = LocalContext.current

    CarCareScreen {
        Text(
            text = "Prikaz servisa",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Pregled poznatih servisnih lokacija")

        Spacer(modifier = Modifier.height(20.dp))

        ServiceLocationCard(
            title = "Auto Zubak servis",
            description = "Lokacija: Osijek\nUsluge: ovlašteni servis, dijagnostika, održavanje vozila",
            searchQuery = "Auto Zubak servis Osijek",
            onOpenMap = { query ->
                openGoogleMaps(context, query)
            }
        )

        ServiceLocationCard(
            title = "Auto servis Tokić",
            description = "Lokacija: Osijek\nUsluge: autodijelovi, servis, održavanje vozila",
            searchQuery = "Auto servis Tokić Osijek",
            onOpenMap = { query ->
                openGoogleMaps(context, query)
            }
        )

        ServiceLocationCard(
            title = "Auto servis Alpina",
            description = "Lokacija: Osijek\nUsluge: servis vozila, popravci, dijagnostika",
            searchQuery = "Auto servis Alpina Osijek",
            onOpenMap = { query ->
                openGoogleMaps(context, query)
            }
        )

        ServiceLocationCard(
            title = "Auto servis Bošnjak",
            description = "Lokacija: Osijek\nUsluge: mehaničarski radovi, održavanje i popravci",
            searchQuery = "Auto servis Bošnjak Osijek",
            onOpenMap = { query ->
                openGoogleMaps(context, query)
            }
        )

        ServiceLocationCard(
            title = "Auto centar Buljubašić",
            description = "Lokacija: Osijek\nUsluge: servis vozila, održavanje i dijagnostika",
            searchQuery = "Auto centar Buljubašić Osijek",
            onOpenMap = { query ->
                openGoogleMaps(context, query)
            }
        )

        BackToDashboardButton(navController)
    }
}

@Composable
fun ServiceLocationCard(
    title: String,
    description: String,
    searchQuery: String,
    onOpenMap: (String) -> Unit
) {
    CarCareCard(
        title = title,
        value = description
    )

    CarCareButton(
        text = "Otvori u Google Maps",
        onClick = {
            onOpenMap(searchQuery)
        }
    )

    Spacer(modifier = Modifier.height(12.dp))
}


fun openGoogleMaps(
    context: Context,
    query: String
) {
    val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")

    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        val browserUri = Uri.parse(
            "https://www.google.com/maps/search/?api=1&query=${Uri.encode(query)}"
        )
        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
        context.startActivity(browserIntent)
    }
}