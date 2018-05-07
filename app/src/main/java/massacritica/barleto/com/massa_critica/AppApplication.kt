package massacritica.barleto.com.massa_critica

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import massacritica.barleto.com.massa_critica.main.MainActivity
import massacritica.barleto.com.massa_critica.main.MassaCriticaData

class AppApplication: Application() {

    companion object {
        const val PREFERENCE_DOMAIN = "PREFERENCE_DOMAIN"
        const val MASSA_CRITICA_DATA = "MASSA_CRITICA_DATA"

        lateinit var instance: AppApplication
    }

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var gson = Gson()

    lateinit var massaCritica: MassaCriticaData

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences = getSharedPreferences(PREFERENCE_DOMAIN, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        retrieveMassaCritica()
    }


    public fun retrieveMassaCritica() {
        val json = sharedPreferences.getString(MASSA_CRITICA_DATA, "")
        massaCritica = if (json == "") {
            MassaCriticaData()
        } else {
            gson.fromJson(json, MassaCriticaData::class.java)
        }
    }

    fun saveMassaCritica() {
        val json = gson.toJson(massaCritica, MassaCriticaData::class.java)
        editor.putString(MainActivity.MASSA_CRITICA_DATA, json)
        editor.apply()
    }

}