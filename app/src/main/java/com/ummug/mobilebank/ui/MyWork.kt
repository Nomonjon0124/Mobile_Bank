package com.ummug.mobilebank.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ummug.mobilebank.MainActivity
import com.ummug.mobilebank.R

class MyWork(context: Context,workerParameter:WorkerParameters,val code:String):Worker(context,workerParameter) {
    companion object{
        const val CHANNEL_ID="channel_id"
        const val NOTIFICATION=1
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        Log.d( "doWork: ","doWork:Success function called")
        shownotification(code)
        return Result.success()
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun shownotification(code: String){
        val intent=Intent(applicationContext, MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent=PendingIntent.getActivity(applicationContext,0,intent, FLAG_MUTABLE)

        val notification=Notification.Builder(applicationContext, CHANNEL_ID)

            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Tasdiqlash kodi")
            .setContentText(code+"Kodni hechga bermang")
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channelName="channel name"
            val channelDescription="channel Desscription"
            val channelImportance=NotificationManager.IMPORTANCE_HIGH

            val channel=NotificationChannel(CHANNEL_ID,channelName,channelImportance).apply {
                description=channelDescription
            }
            val notificationManager=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(NOTIFICATION,notification.build())
        }

    }



}