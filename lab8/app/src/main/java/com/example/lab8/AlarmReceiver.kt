package com.example.lab8

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.os.Build
import android.os.SystemClock
import android.provider.Settings

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val typeId = intent.getIntExtra("Type", 0)
        val delay = intent.getIntExtra("Delay", 0)
        if (delay > 0) {
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent.putExtra("Delay", 0), 0)
            val alarmManager = context.getSystemService(Service.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay * 1000, pendingIntent)
        }
        else {
            val notificationManager =
                context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel("0", "My channel", NotificationManager.IMPORTANCE_HIGH)
                channel.description = "My channel description"
                channel.enableLights(true)
                channel.enableVibration(true)
                val att =
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
                channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, att)
                notificationManager.createNotificationChannel(channel)
            }
            val contentIntent = Intent(context, MainActivity::class.java)
            val pintent = PendingIntent.getActivity(context, 0, contentIntent, 0)
            val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, "0")
            } else {
                Notification.Builder(context)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_MAX)
            }
                .setContentTitle("Заголовок уведомления")
                .setContentText("Текст уведомления")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pintent)
                .setAutoCancel(true)
            when (typeId) {
                R.id.button_btn -> {
                    notificationManager.cancel(typeId)
                    //val pintent = PendingIntent.getService(application, 0, intent.putExtra("delay", 10), 0)
                    //builder.addAction(Notification.Action.Builder(0, "Отложить", pintent).build())
                }
                R.id.button_txt -> {
                    builder.style = Notification.BigTextStyle()
                        .setBigContentTitle("Альберт Эйнштейн")
                        .bigText("Теория — это когда все известно, но ничего не работает. Практика — это когда все работает, но никто не знает почему. Мы же объединяем теорию и практику: ничего не работает... и никто не знает почему!")
                }
                R.id.button_img -> {
                    builder.style = Notification.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.resources, R.drawable.car))
                }
                R.id.button_inbox -> {
                    builder.style = Notification.InboxStyle()
                        .addLine("Мороз и солнце; день чудесный!")
                        .addLine("Еще ты дремлешь, друг прелестный -")
                        .addLine("Пора, красавица, проснись:")
                        .addLine("Открой сомкнуты негой взоры")
                        .addLine("Навстречу северной Авроры,")
                        .addLine("Звездою севера явись!")
                        .setSummaryText("А.С.Пушкин")
                }
            }
            notificationManager.notify(typeId, builder.build())
        }
    }
}
