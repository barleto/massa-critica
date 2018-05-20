package massacritica.barleto.com.massa_critica.notification.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import massacritica.barleto.com.massa_critica.AppApplication
import massacritica.barleto.com.massa_critica.R
import massacritica.barleto.com.massa_critica.notification.TripNotification
import java.text.SimpleDateFormat
import java.util.*


class TripNotificationAdapter(private val tripNotificationList: MutableList<TripNotification> ) : RecyclerView.Adapter<TripNotificationCellHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripNotificationCellHolder {
        return TripNotificationCellHolder(LayoutInflater.from(parent.context).inflate(R.layout.trip_notification_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return tripNotificationList.count()
    }

    override fun onBindViewHolder(holder: TripNotificationCellHolder, position: Int) {
        val tripNotification = tripNotificationList[position]
        val c = Calendar.getInstance()
        c.set(Calendar.DAY_OF_WEEK,tripNotification.dayOfWeek)
        c.set(Calendar.HOUR_OF_DAY,tripNotification.hour)
        c.set(Calendar.MINUTE,tripNotification.minute)
        val dateFormat = SimpleDateFormat("E, HH:mm", Locale.getDefault())
        val timeString = dateFormat.format(c.time)
        holder.dateHolder.text = timeString
        holder.deleteButton.setOnClickListener {
            val app = AppApplication.instance
            val massaCritica = app.massaCritica
            massaCritica.tripNotificationList.remove(tripNotification)
            app.saveMassaCritica()
            tripNotification.cancelNotification(app.applicationContext)
            notifyDataSetChanged()
        }
    }

}