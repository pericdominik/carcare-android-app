package com.dperic.carcare.notification

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

object ReminderNotificationScheduler {

    fun scheduleReminder(
        context: Context,
        title: String,
        date: String,
        time: String
    ) {
        try {
            val dateTimeText = "$date $time"
            val format = SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault())
            val parsedDate = format.parse(dateTimeText) ?: return

            val triggerTime = parsedDate.time
            val currentTime = System.currentTimeMillis()
            val delay = triggerTime - currentTime

            if (delay <= 0) {
                return
            }

            val inputData = workDataOf(
                "title" to "CarCare podsjetnik",
                "message" to title
            )

            val notificationWorkRequest =
                OneTimeWorkRequestBuilder<ReminderNotificationWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(inputData)
                    .build()

            WorkManager
                .getInstance(context.applicationContext)
                .enqueue(notificationWorkRequest)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}