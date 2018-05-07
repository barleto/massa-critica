package massacritica.barleto.com.massa_critica.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import massacritica.barleto.com.massa_critica.R

class TripListAdapter(val tripList: MutableList<TripData>, val mainActivity: MainActivity) : RecyclerView.Adapter<TripCellHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripCellHolder {
        return TripCellHolder(LayoutInflater.from(parent.context).inflate(R.layout.trip_made_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return tripList.count()
    }

    override fun onBindViewHolder(holder: TripCellHolder, position: Int) {
        val pos = tripList.count() - 1 - position
        var trip = tripList[pos]
        holder.dateHolder.text = "${position}. ${trip.date}"
        holder.deleteButton.setOnClickListener{
            mainActivity.massaCritica.unmakeTrip(trip)
            mainActivity.saveMassaCritica()
            notifyDataSetChanged()
        }
    }

}
