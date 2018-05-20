package massacritica.barleto.com.massa_critica.notification.list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_trip_notification.*
import massacritica.barleto.com.massa_critica.AppApplication
import massacritica.barleto.com.massa_critica.R
import massacritica.barleto.com.massa_critica.notification.create.CreateTripNotificationActivity

class TripNotificationActivity : AppCompatActivity() {

    val massaCritica = AppApplication.instance.massaCritica

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_notification)


        val layoutManager = LinearLayoutManager(this)
        trip_notification_list.layoutManager = layoutManager
        trip_notification_list.adapter = TripNotificationAdapter(massaCritica.tripNotificationList)

        create_alarm_button.setOnClickListener {
            val intent = Intent(this, CreateTripNotificationActivity::class.java)
            startActivityForResult(intent,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        trip_notification_list.adapter.notifyDataSetChanged()
    }
}
