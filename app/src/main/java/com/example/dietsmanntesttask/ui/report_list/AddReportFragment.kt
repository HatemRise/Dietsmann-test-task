package com.example.dietsmanntesttask.ui.report_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Day
import com.example.dietsmanntesttask.entities.Temperature
import com.example.dietsmanntesttask.ui.TemperatureFragment
import java.text.SimpleDateFormat
import java.util.*

class AddReportFragment(var report: Day =
    Day(true,
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).format(Calendar.getInstance().time),
    "",
    arrayListOf(),
    arrayListOf())) : Fragment() , ReportContactListAdapter.OnItemClickListener{
    val adapter = ReportContactListAdapter(report.contacts, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_report, container, false)
        report.temperats.forEach {
            addTemperature(view, it.temperat.toString(), it.time.substringAfter('T'))
        }
        (activity as MainActivity).findViewById<AppCompatButton>(R.id.add).isEnabled = false
        view.findViewById<EditText>(R.id.report_temperature_field).setText("36.6")
        view.findViewById<TextView>(R.id.report_time_field).text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Calendar.getInstance().time)
        view.findViewById<TextView>(R.id.report_date_field).text =
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                .format(SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm",
                Locale.getDefault())
                .parse(report.date))
        val contactsList : RecyclerView = view.findViewById(R.id.report_contacts_container)
        contactsList.adapter = adapter
        contactsList.layoutManager = LinearLayoutManager(activity)
        buttonsListeners(view)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            (activity as MainActivity).findViewById<AppCompatButton>(R.id.add).isEnabled = true
        }catch (e: Exception){
            return
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "Удерживайте для удаления", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int) {
        report.contacts.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    private fun buttonsListeners(view: View){
        view.findViewById<AppCompatButton>(R.id.report_close_button).setOnClickListener {
            (activity as MainActivity).temperatureFragment()
        }
        view.findViewById<AppCompatButton>(R.id.report_add_contact).setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction().add(R.id.new_contact_add_contact_panel, AddContactToReportFragment()).commit()
        }
        view.findViewById<AppCompatButton>(R.id.report_save_button).setOnClickListener {
            if (report.temperats.isEmpty()){
                Toast.makeText(activity, "Добавьте температуру", Toast.LENGTH_SHORT).show()
            }else {
                report.temperats.forEach {
                    it.time = report.date.replaceAfter('T', it.time)
                }
                Thread {
                    (activity as MainActivity).connection.save(report)
                }.start()
                (activity as MainActivity).temperatureFragment()
                (activity as MainActivity).getWeek()
            }
        }
        view.findViewById<AppCompatButton>(R.id.report_add_temperature).setOnClickListener {
            val temperature = view.findViewById<EditText>(R.id.report_temperature_field).text.toString()
            val time = view.findViewById<TextView>(R.id.report_time_field).text.toString()
            addTemperature(view, temperature, time)
            report.temperats.add(Temperature(temperature.toFloat(), time))
        }
    }
    private fun addTemperature(view: View, temperature: String, time: String){
        (activity as MainActivity).supportFragmentManager.beginTransaction().add(R.id.report_temperature_container, TemperatureFragment(
                temperature,
                time
            )
        ).commit()
    }
}