package com.example.dietsmanntesttask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dietsmanntesttask.connect.Connect
import com.example.dietsmanntesttask.connect.Connection
import com.example.dietsmanntesttask.entities.Day
import com.example.dietsmanntesttask.entities.User
import com.example.dietsmanntesttask.ui.ListFragment
import com.example.dietsmanntesttask.ui.login.LoginFragment
import com.example.dietsmanntesttask.ui.MainFragment
import com.example.dietsmanntesttask.ui.contacts.ContactsFragment
import com.example.dietsmanntesttask.ui.contacts.GroupContactsFragment
import com.example.dietsmanntesttask.ui.report_list.AddReportFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    lateinit var week: List<Day>
    lateinit var connection: Connection
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connection = Connect(this)
        getSettings()
//        plug()
    }

    private fun getSettings() {
        val setting = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = setting.getString("token", null)
        val role = setting.getString("role", null)
        val name = setting.getString("name", null)
        if (token == null || name == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_container, LoginFragment()).commit()
        } else {
            connection.setUser(token, role ?: "User", name)
            starMainScreen(name)
        }
    }

    fun saveUser(user: User) {
        val setting = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        setting.edit().apply {
            this.putString("token", user.empid)
            this.putString("role", user.role)
            this.putString("name", user.fio)
        }.apply()
        starMainScreen(user.fio)
    }

    fun starMainScreen(fio: String) {
        Thread {
            week = connection.getWeek()!!
            supportFragmentManager.beginTransaction().replace(R.id.main_container, MainFragment()).commit()
            Handler(Looper.getMainLooper()).post {
                findViewById<TextView>(R.id.userFIO).text = fio
            }
            temperatureFragment()
        }.start()
    }

    fun temperatureFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.contentPanel, ListFragment()).commit()
    }

    fun contactsFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.contentPanel, ContactsFragment()).commit()
    }

    fun logout() {
        val setting = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        setting.edit().remove("token").apply()
        setting.edit().remove("role").apply()
        setting.edit().remove("name").apply()
        getSettings()
    }

    lateinit var addReportFragment: AddReportFragment
    fun addReport(position: Int?) {
        if(position != null) {
            addReportFragment = AddReportFragment(week[position])
        }else {
            addReportFragment = AddReportFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.contentPanel, addReportFragment).commit()
    }

    fun contactsButton(group: Short) {
        Thread {
            val contacts = connection.getGroupContacts(group)
            if (contacts.isEmpty()) {
                Toast.makeText(this, "Группа пуста", Toast.LENGTH_SHORT).show()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.contentPanel, GroupContactsFragment(contacts)).commit()
            }
        }.start()
    }

    fun makeDatePicker(view: View) {
        DatePickerDialog(
            this,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun makeTimePicker(view: View) {
        TimePickerDialog(
            this,
            this,
            Calendar.getInstance().get(Calendar.HOUR),
            Calendar.getInstance().get(Calendar.MINUTE),
            true).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance()
        date.set(year, month, dayOfMonth)
        addReportFragment.report.date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).format(date.time)
        findViewById<TextView>(R.id.report_date_field).text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date.time)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val date = Calendar.getInstance()
        date.set(Calendar.HOUR, hourOfDay)
        date.set(Calendar.MINUTE, minute)
        findViewById<TextView>(R.id.report_time_field).text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date.time)
    }

    fun getWeek() {
        Thread {
            week = connection.getWeek()!!
        }.start()
    }
}