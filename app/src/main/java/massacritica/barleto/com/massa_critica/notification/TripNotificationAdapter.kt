package massacritica.barleto.com.massa_critica.notification

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import massacritica.barleto.com.massa_critica.R
import massacritica.barleto.com.massa_critica.main.MainActivity

class TripNotificationAdapter(val tripNotificationList: MutableList<TripNotification> ) : RecyclerView.Adapter<TripNotificationCellHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripNotificationCellHolder {
        return TripNotificationCellHolder(LayoutInflater.from(parent.context).inflate(R.layout.trip_made_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return tripNotificationList.count()
    }

    override fun onBindViewHolder(holder: TripNotificationCellHolder, position: Int) {
        val tripNotification = tripNotificationList[position]
        holder.dateHolder.text = tripNotification.date.toString()
        holder.detailsButton.setOnClickListener {
            //TODO
        }
    }

}