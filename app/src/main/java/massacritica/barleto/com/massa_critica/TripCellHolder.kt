package massacritica.barleto.com.massa_critica

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class TripCellHolder(val view: View) : RecyclerView.ViewHolder(view) {

    lateinit var dateHolder: TextView
    lateinit var deleteButton: ImageView

    init {
        dateHolder = view.findViewById(R.id.cell_date)
        deleteButton = view.findViewById(R.id.cell_delete)
    }

}
