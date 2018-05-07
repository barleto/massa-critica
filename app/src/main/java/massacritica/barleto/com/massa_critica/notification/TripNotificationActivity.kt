package massacritica.barleto.com.massa_critica.notification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_trip_notification.*
import massacritica.barleto.com.massa_critica.AppApplication
import massacritica.barleto.com.massa_critica.R

class TripNotificationActivity : AppCompatActivity() {

    val massaCritica = AppApplication.instance.massaCritica

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_notification)

        trip_notification_list.adapter = TripNotificationAdapter(massaCritica.tripNotificationList)

    }
}
