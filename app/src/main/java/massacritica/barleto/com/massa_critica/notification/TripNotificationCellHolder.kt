package massacritica.barleto.com.massa_critica.notification

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import massacritica.barleto.com.massa_critica.R

class TripNotificationCellHolder(val view: View) : RecyclerView.ViewHolder(view) {

    var dateHolder: TextView
    var detailsButton: ImageView

    init {
        dateHolder = view.findViewById(R.id.cell_notification_date)
        detailsButton = view.findViewById(R.id.cell_notification_details)
    }

}