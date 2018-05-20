package massacritica.barleto.com.massa_critica.notification.create

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_create_notification.*
import massacritica.barleto.com.massa_critica.AppApplication
import massacritica.barleto.com.massa_critica.R
import massacritica.barleto.com.massa_critica.notification.TripNotification
import java.util.*
import kotlin.math.min

class CreateTripNotificationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener {

    var dayOfWeek: Int = Calendar.SUNDAY
    var hour = 0
    var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notification)

        val spinner = alarm_day_of_week_spinner
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week,
                android.R.layout.simple_dropdown_item_1line)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        alarm_time_picker.setIs24HourView(true)
        alarm_time_picker.setOnTimeChangedListener(this)

        create_alarm_button.setOnClickListener {
            createTripNotification()
            finish()
        }
    }

    private fun createTripNotification() {
        val massaCritica = AppApplication.instance.massaCritica
        val not = TripNotification(dayOfWeek,hour,minute, UUID.randomUUID().toString())
        massaCritica.tripNotificationList.add(not)
        not.scheduleNotification(applicationContext)
        AppApplication.instance.saveMassaCritica()
    }
//    54979922-4bbc-4a89-bba6-691e89a888db
    override fun onNothingSelected(parent: AdapterView<*>?)  = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        dayOfWeek = when(pos){
            0->Calendar.SUNDAY
            1->Calendar.MONDAY
            2->Calendar.TUESDAY
            3->Calendar.WEDNESDAY
            4->Calendar.THURSDAY
            5->Calendar.FRIDAY
            6->Calendar.SATURDAY
            else -> Calendar.SUNDAY
        }
    }

    override fun onTimeChanged(timePicker: TimePicker?, hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
    }
}
