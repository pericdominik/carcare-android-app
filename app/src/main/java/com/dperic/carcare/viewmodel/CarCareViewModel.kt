package com.dperic.carcare.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.dperic.carcare.model.Reminder
import com.dperic.carcare.model.ServiceRecord
import com.dperic.carcare.model.Vehicle
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dperic.carcare.BuildConfig
import com.dperic.carcare.network.RetrofitInstance

class CarCareViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    var weatherResult by mutableStateOf("")
        private set

    var weatherError by mutableStateOf("")
        private set

    var isWeatherLoading by mutableStateOf(false)
        private set


    init {
        loadVehicles()
        loadServiceRecords()
        loadReminders()
    }

    val vehicles = mutableStateListOf<Vehicle>()
    val serviceRecords = mutableStateListOf<ServiceRecord>()
    val reminders = mutableStateListOf<Reminder>()

    fun loadVehicles() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("vehicles")
                    .get()
                    .await()

                vehicles.clear()

                snapshot.documents.forEach { document ->
                    val vehicle = document.toObject(Vehicle::class.java)

                    if (vehicle != null) {
                        vehicles.add(vehicle)
                    }
                }
            } catch (e: Exception) {
                // Za sada ne prikazujemo grešku u UI-u
            }
        }
    }

    fun loadServiceRecords() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("serviceRecords")
                    .get()
                    .await()

                serviceRecords.clear()

                snapshot.documents.forEach { document ->
                    val serviceRecord = document.toObject(ServiceRecord::class.java)

                    if (serviceRecord != null) {
                        serviceRecords.add(serviceRecord)
                    }
                }
            } catch (e: Exception) {
                // Za sada ne prikazujemo grešku u UI-u
            }
        }
    }

    fun loadReminders() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("reminders")
                    .get()
                    .await()

                reminders.clear()

                snapshot.documents.forEach { document ->
                    val reminder = document.toObject(Reminder::class.java)

                    if (reminder != null) {
                        reminders.add(reminder)
                    }
                }
            } catch (e: Exception) {
                // Za sada ne prikazujemo grešku u UI-u
            }
        }
    }


    fun addVehicle(
        brand: String,
        model: String,
        year: String,
        mileage: String
    ) {
        val newVehicle = Vehicle(
            id = System.currentTimeMillis().toString(),
            brand = brand,
            model = model,
            year = year,
            mileage = mileage
        )

        vehicles.add(newVehicle)

        db.collection("vehicles")
            .document(newVehicle.id)
            .set(newVehicle)
    }

    fun deleteVehicle(vehicleId: String) {
        vehicles.removeAll { vehicle ->
            vehicle.id == vehicleId
        }

        db.collection("vehicles")
            .document(vehicleId)
            .delete()
    }


    fun addServiceRecord(
        vehicleId: String,
        type: String,
        date: String,
        mileage: String,
        price: String,
        note: String
    ) {
        val newServiceRecord = ServiceRecord(
            id = System.currentTimeMillis().toString(),
            vehicleId = vehicleId,
            type = type,
            date = date,
            mileage = mileage,
            price = price,
            note = note
        )

        serviceRecords.add(newServiceRecord)

        db.collection("serviceRecords")
            .document(newServiceRecord.id)
            .set(newServiceRecord)
    }

    fun deleteServiceRecord(serviceId: String) {
        serviceRecords.removeAll { service ->
            service.id == serviceId
        }

        db.collection("serviceRecords")
            .document(serviceId)
            .delete()
    }


    fun addReminder(
        vehicleId: String,
        title: String,
        date: String,
        time: String
    ) {
        val newReminder = Reminder(
            id = System.currentTimeMillis().toString(),
            vehicleId = vehicleId,
            title = title,
            date = date,
            time = time,
            isDone = false
        )

        reminders.add(newReminder)

        db.collection("reminders")
            .document(newReminder.id)
            .set(newReminder)
    }

    fun getTotalServiceCost(): Int {
        return serviceRecords.sumOf { service ->
            service.price.toIntOrNull() ?: 0
        }
    }

    fun deleteReminder(reminderId: String) {
        reminders.removeAll { reminder ->
            reminder.id == reminderId
        }

        db.collection("reminders")
            .document(reminderId)
            .delete()
    }

    fun completeReminder(reminderId: String) {
        val reminderIndex = reminders.indexOfFirst { reminder ->
            reminder.id == reminderId
        }

        if (reminderIndex != -1) {
            val updatedReminder = reminders[reminderIndex].copy(isDone = true)

            reminders[reminderIndex] = updatedReminder

            db.collection("reminders")
                .document(reminderId)
                .set(updatedReminder)
        }
    }


    fun fetchWeather(city: String) {
        if (city.isBlank()) {
            weatherError = "Unesite naziv grada."
            weatherResult = ""
            return
        }

        if (BuildConfig.OPENWEATHER_API_KEY.isBlank()) {
            weatherError = "API ključ nije pronađen."
            weatherResult = ""
            return
        }

        viewModelScope.launch {
            try {
                isWeatherLoading = true
                weatherError = ""
                weatherResult = ""

                val response = RetrofitInstance.api.getWeatherByCity(
                    city = city,
                    apiKey = BuildConfig.OPENWEATHER_API_KEY
                )

                val description = response.weather.firstOrNull()?.description ?: "Nema opisa"

                weatherResult =
                    "Grad: ${response.name}\n" +
                            "Temperatura: ${response.main.temp} °C\n" +
                            "Vlažnost: ${response.main.humidity}%\n" +
                            "Vrijeme: $description\n" +
                            "Uvjeti za vožnju: ${getDrivingCondition(response.main.temp, description)}"

            } catch (e: Exception) {
                weatherError = "Nije moguće dohvatiti vremenske podatke."
            } finally {
                isWeatherLoading = false
            }
        }
    }

    private fun getDrivingCondition(
        temperature: Double,
        description: String
    ): String {
        return when {
            description.contains("kiša", ignoreCase = true) -> "Oprez, cesta može biti mokra"
            description.contains("snijeg", ignoreCase = true) -> "Oprez, mogući su teški uvjeti"
            description.contains("magla", ignoreCase = true) -> "Oprez, smanjena vidljivost"
            temperature < 0 -> "Oprez, moguća poledica"
            else -> "Dobri"
        }
    }
}