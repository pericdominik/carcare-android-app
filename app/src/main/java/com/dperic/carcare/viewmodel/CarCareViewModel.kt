package com.dperic.carcare.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.dperic.carcare.model.Reminder
import com.dperic.carcare.model.ServiceRecord
import com.dperic.carcare.model.Vehicle
import com.google.firebase.firestore.FirebaseFirestore

class CarCareViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    val vehicles = mutableStateListOf(
        Vehicle(
            id = "1",
            brand = "Volkswagen",
            model = "Golf 7",
            year = "2016",
            mileage = "175000"
        )
    )

    val serviceRecords = mutableStateListOf(
        ServiceRecord(
            id = "1",
            vehicleId = "1",
            type = "Zamjena ulja i filtera",
            date = "20.05.2026.",
            mileage = "175000",
            price = "120",
            note = "Redovni servis"
        )
    )

    val reminders = mutableStateListOf(
        Reminder(
            id = "1",
            vehicleId = "1",
            title = "Zamjena ulja",
            date = "Za 30 dana",
            isDone = false
        )
    )

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

    fun addReminder(
        vehicleId: String,
        title: String,
        date: String
    ) {
        val newReminder = Reminder(
            id = System.currentTimeMillis().toString(),
            vehicleId = vehicleId,
            title = title,
            date = date,
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
}