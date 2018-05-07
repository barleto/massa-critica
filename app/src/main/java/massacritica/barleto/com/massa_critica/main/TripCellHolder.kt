package massacritica.barleto.com.massa_critica.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import massacritica.barleto.com.massa_critica.R

class TripCellHolder(val view: View) : RecyclerView.ViewHolder(view) {

    var dateHolder: TextView
    var deleteButton: ImageView

    init {
        dateHolder = view.findViewById(R.id.cell_date)
        deleteButton = view.findViewById(R.id.cell_delete)
    }

}
