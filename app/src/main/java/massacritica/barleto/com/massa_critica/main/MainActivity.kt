package massacritica.barleto.com.massa_critica.main

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import massacritica.barleto.com.massa_critica.R


class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFERENCE_DOMAIN = "PREFERENCE_DOMAIN"
        const val MASSA_CRITICA_DATA = "MASSA_CRITICA_DATA"
    }

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var gson = Gson()
    lateinit var massaCritica: MassaCriticaData
    lateinit var adapter: TripListAdapter

    data class DepositData(var date: String, var value: Float)

    data class TripData(var date: String, var price: Float)

    class MassaCriticaData {
        var passagePrice = 0f
        var currentBalance = 0f
        var depositsList: MutableList<DepositData> = mutableListOf()
        var tripList: MutableList<TripData> = mutableListOf()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREFERENCE_DOMAIN, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        retrieveMassaCritica()

        updateUI()

        updateTripList()

        setListeners()

    }

    private fun updateTripList() {
        val layoutManager = LinearLayoutManager(this)
        trip_list.setLayoutManager(layoutManager)

        adapter = TripListAdapter(massaCritica.tripList, this)
        trip_list.adapter = adapter

        // Configurando um dividr entre linhas, para uma melhor visualização.
        trip_list.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    private fun setListeners() {
        update_price.setOnClickListener {
            massaCritica.passagePrice = passage_price.text.toString().toFloat()
            hideSoftKeyBoard()
            saveMassaCritica()
        }

        make_deposit.setOnClickListener {
            val depositVal = deposit_amount.text.toString().toFloat()
            massaCritica.makeDeposit(depositVal)
            hideSoftKeyBoard()
            saveMassaCritica()
        }

        unmake_last_deposit.setOnClickListener {
            massaCritica.unmakeDeposit()
            hideSoftKeyBoard()
            saveMassaCritica()
        }

        make_trip.setOnClickListener {
            massaCritica.makeTrip()
            hideSoftKeyBoard()
            saveMassaCritica()
            updateTripList()
        }
    }

    private fun retrieveMassaCritica() {
        val json = sharedPreferences.getString(MASSA_CRITICA_DATA, "")
        massaCritica = if (json == "") {
            MassaCriticaData()
        } else {
            gson.fromJson(json, MassaCriticaData::class.java)
        }
    }

    fun saveMassaCritica() {
        val json = gson.toJson(massaCritica, MassaCriticaData::class.java)
        editor.putString(MASSA_CRITICA_DATA, json)
        editor.apply()
        updateUI()
    }

    private fun updateUI() {
        passage_price.setText(massaCritica.passagePrice.toString(), TextView.BufferType.EDITABLE)
        current_balance.text = massaCritica.currentBalance.toString()
        last_deposit_date.text = if (massaCritica.depositsList.count() > 0) {
            massaCritica.depositsList.last().date
        } else {
            "Unknown"
        }
        val passagesLeft = massaCritica.passagesLeft()
        total_trips_left.text = "${passagesLeft} passagens faltantes."
        if (passagesLeft > 0) {
            total_trips_left.setTextColor(Color.GREEN)
        } else if (passagesLeft < 0) {
            total_trips_left.setTextColor(Color.RED)
        } else {
            total_trips_left.setTextColor(Color.GRAY)
        }
    }

    private fun hideSoftKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm.isAcceptingText) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

}
