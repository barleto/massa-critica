package massacritica.barleto.com.massa_critica.main

import massacritica.barleto.com.massa_critica.notification.TripNotification
import java.text.SimpleDateFormat
import java.util.*

data class DepositData(var date: String, var value: Float)

data class TripData(var date: String, var price: Float)

class MassaCriticaData {
    var passagePrice = 0f
    var currentBalance = 0f
    var depositsList: MutableList<DepositData> = mutableListOf()
    var tripList: MutableList<TripData> = mutableListOf()
    var tripNotificationList: MutableList<TripNotification> = mutableListOf()

    fun passagesLeft(): Float {
        if(passagePrice == 0f){
            return Float.POSITIVE_INFINITY
        }
        return currentBalance / passagePrice
    }

    fun makeDeposit(depositValue: Float){
        currentBalance += depositValue
        depositsList.add(DepositData(getTodaysDate(), depositValue))
    }

    fun unmakeDeposit() {
        if(depositsList.count() < 1){
            return
        }
        val lastDeposit = depositsList.last()
        depositsList.remove(lastDeposit)
        currentBalance -= lastDeposit.value
    }

    fun makeTrip() {
        currentBalance -= passagePrice
        tripList.add(TripData(getTodaysDate(), passagePrice))
    }

    fun unmakeTrip(trip: TripData) {
        tripList.remove(trip)
        currentBalance += passagePrice
    }

    private fun getTodaysDate(): String {
        val timeStamp = SimpleDateFormat("dd/MM/yyyy (EEEE)").format(Calendar.getInstance().getTime())
        return timeStamp
    }

}