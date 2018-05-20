package massacritica.barleto.com.massa_critica.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import massacritica.barleto.com.massa_critica.AppApplication
import massacritica.barleto.com.massa_critica.R
import massacritica.barleto.com.massa_critica.notification.TripNotification
import massacritica.barleto.com.massa_critica.notification.list.TripNotificationActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val MASSA_CRITICA_DATA = "MASSA_CRITICA_DATA"
    }

    lateinit var massaCritica: MassaCriticaData
    lateinit var adapter: TripListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

        notification_button.setOnClickListener {
            val intent = Intent(this, TripNotificationActivity::class.java)

            startActivity(intent)
        }

    }

    private fun retrieveMassaCritica() {
        massaCritica = AppApplication.instance.massaCritica
    }

    fun saveMassaCritica() {
        AppApplication.instance.saveMassaCritica()
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
