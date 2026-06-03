package com.dperic.carcare.model

data class ServiceRecord(
    val id: String = "",
    val vehicleId: String = "",
    val type: String = "",
    val date: String = "",
    val mileage: String = "",
    val price: String = "",
    val note: String = ""
)