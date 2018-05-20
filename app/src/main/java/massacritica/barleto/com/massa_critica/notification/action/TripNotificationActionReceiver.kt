package massacritica.barleto.com.massa_critica.notification.action

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import massacritica.barleto.com.massa_critica.AppApplication
import massacritica.barleto.com.massa_critica.notification.TripNotification

class TripNotificationActionReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val didMakeTrip = intent?.getBooleanExtra(TripNotification.INTENT_KEY,false) ?: false
        Log.e("TripNotification","Action $didMakeTrip recieved")
        if(didMakeTrip){
            AppApplication.instance.massaCritica.makeTrip()
            AppApplication.instance.saveMassaCritica()
        }
        val notificationManager = NotificationManagerCompat.from(AppApplication.instance.applicationContext)
        notificationManager.cancelAll()
    }
}