package com.dperic.carcare.model

data class Reminder(
    val id: String = "",
    val vehicleId: String = "",
    val title: String = "",
    val date: String = "",
    val isDone: Boolean = false
)