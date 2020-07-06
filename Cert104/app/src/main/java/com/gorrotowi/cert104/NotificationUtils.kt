package com.gorrotowi.cert104

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

const val MY_NOTIFICATION_CHANNEL = "my_notification_channel"
const val NOTIFICATION_ID = 0

fun String.imprimir() {
    Log.e("LOG", "$this world")
}


fun Context.createNotification() {

    val noticationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        noticationManager.createNotificationChannel(createNotificationChannel())
    }

    val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        0,
        intent,
        0
    )

    val builderNotification = NotificationCompat.Builder(this, MY_NOTIFICATION_CHANNEL).apply {
        setSmallIcon(R.drawable.ic_notification_small_icon)
        setContentTitle("Titulo de la notificación")
        setContentText("Notificacion del curso de certificación de Android para Google")
        setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Notificacion del curso de certificación de Android para Google para tener un texto mas grande en nuestro view al momento de abrir la notification")
        )
        setContentIntent(pendingIntent)
        priority = NotificationCompat.PRIORITY_DEFAULT
        setAutoCancel(true)
    }

    NotificationManagerCompat.from(applicationContext)
        .notify(NOTIFICATION_ID, builderNotification.build())

}

@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(): NotificationChannel {
    return NotificationChannel(
        MY_NOTIFICATION_CHANNEL,
        "Cert App Notification Channel",
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        enableVibration(true)
        enableLights(true)
        lightColor = Color.BLUE
        description = "Noficaciones para el curso de Android"
    }
}
