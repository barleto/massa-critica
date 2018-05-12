package massacritica.barleto.com.massa_critica.notification

import android.content.Context
import java.util.*
import android.util.Log
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent


class TripNotification(var calendar: Calendar, var notificationId: Int) {

    companion object {
        const val INTENT_KEY = "trip_notification_bundle_key"
    }

    fun scheduleNotification(context: Context) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TripNotificationAlarm::class.java)
        intent.putExtra(INTENT_KEY,notificationId)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        Log.d("TripNotification","Scheduling notification ${calendar}")
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
    }

}