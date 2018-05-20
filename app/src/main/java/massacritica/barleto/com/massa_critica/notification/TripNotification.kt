package massacritica.barleto.com.massa_critica.notification

import android.content.Context
import java.util.*
import android.util.Log
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import massacritica.barleto.com.massa_critica.AppApplication
import android.support.v4.app.NotificationManagerCompat




class TripNotification(val dayOfWeek: Int, val hour: Int, val minute: Int, val notificationId: String) {

    var requestCode: Int

    init{
        requestCode = Calendar.getInstance().timeInMillis.toInt()
    }

    companion object {
        const val INTENT_KEY = "trip_notification_bundle_key"
    }

    fun scheduleNotification(context: Context) {
        val calendar = nextDayOfWeek(dayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        val (alarmMgr, alarmIntent) = createPendingIntent(context)

        Log.e("TripNotification","Scheduling notification to ${calendar.time}, with id $notificationId")
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
    }

    private fun createPendingIntent(context: Context): Pair<AlarmManager, PendingIntent> {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TripNotificationBroadcastReceiver::class.java)
        intent.putExtra(INTENT_KEY, notificationId)

        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return Pair(alarmMgr, alarmIntent)
    }

    fun nextDayOfWeek(dayOfWeek: Int): Calendar {
        val now = Calendar.getInstance()
        val date = Calendar.getInstance()
        date.set(Calendar.HOUR_OF_DAY,hour)
        date.set(Calendar.MINUTE, minute)
        if(date.after(now)){
            return date
        }

        var diff = dayOfWeek - date.get(Calendar.DAY_OF_WEEK)
        if (diff <= 0) {
            diff += 7
        }
        date.add(Calendar.DAY_OF_MONTH, diff)
        return date
    }

    fun cancelNotification(context: Context) {
        Log.e("TripNotification","Canceling notification.")
        val pair = createPendingIntent(context)
        val pendingIntent = pair.second
        val alarmMgr = pair.first
        alarmMgr.cancel(pendingIntent)
    }

    fun activateNotification(){
        val mBuilder = NotificationCompat.Builder(AppApplication.instance.applicationContext, "TripNotification")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Teste")
                .setContentText("Isso é um teste")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(AppApplication.instance.applicationContext)
        notificationManager.notify(0, mBuilder.build())

    }

}