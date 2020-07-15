package com.gorrotowi.cert108

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


const val MY_NOTIFICATION_CHANNEL = "LocationService"
const val NOTIFICATION_ID = 422

fun Context.createNotification(): Notification? {

    val noticationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        noticationManager.createNotificationChannel(createNotificationChannel())
    }

    val notificationIntent = Intent(this, MainActivity::class.java)
    val pendingIntentNotification = PendingIntent.getActivity(
        this,
        0, notificationIntent, 0
    )

    val builderNotification = NotificationCompat.Builder(this, MY_NOTIFICATION_CHANNEL).apply {
        setSmallIcon(R.drawable.ic_location)
        setContentTitle("Te estamos siguiendo 6_6")
        setContentText("Camina, aun te faltan pasos")
        setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Camina, aun te faltan pasos, recuerdan que son 10,000 diarios")
        )
        setContentIntent(pendingIntentNotification)
        priority = NotificationCompat.PRIORITY_MIN
//        setNotificationSilent()
        setAutoCancel(true)
    }

//    NotificationManagerCompat.from(applicationContext)
//        .notify(NOTIFICATION_ID, builderNotification.build())
    return builderNotification.build()
}

@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(): NotificationChannel {
    return NotificationChannel(
        MY_NOTIFICATION_CHANNEL,
        "Cert App Notification Channel",
        NotificationManager.IMPORTANCE_MIN
    ).apply {
        enableVibration(true)
        enableLights(true)
        lightColor = Color.BLUE
        description = "Noficaciones para el seguimiento del usuario"
    }
}
