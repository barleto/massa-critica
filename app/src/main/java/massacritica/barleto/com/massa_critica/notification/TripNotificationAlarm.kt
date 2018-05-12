package massacritica.barleto.com.massa_critica.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import massacritica.barleto.com.massa_critica.AppApplication

class TripNotificationAlarm: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getIntExtra(TripNotification.INTENT_KEY,-1)
        val notification = AppApplication.instance.massaCritica.tripNotificationList.find {
            return@find it.notificationId == notificationId
        }
        Log.e("TripNotification", "FOI!!")
        notification?.scheduleNotification(AppApplication.instance.applicationContext)
    }

}