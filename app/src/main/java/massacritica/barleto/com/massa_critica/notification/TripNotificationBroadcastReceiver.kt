package massacritica.barleto.com.massa_critica.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import massacritica.barleto.com.massa_critica.AppApplication

class TripNotificationBroadcastReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getStringExtra(TripNotification.INTENT_KEY)

        val notification = AppApplication.instance.massaCritica.tripNotificationList.find {
            return@find it.notificationId == notificationId
        }
        Log.e("TripNotification", "Recieved alarm for notificationId $notificationId")
        notification?.activateNotification()
        notification?.scheduleNotification(AppApplication.instance.applicationContext)
    }

}