package com.example.dietsmanntesttask.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Temperature
import java.text.SimpleDateFormat
import java.util.*

class TemperatureFragment(private var temperature: String, private var time: String) : Fragment() {
    lateinit var temperat: Temperature
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_temperature, container, false)
        if(temperature.isBlank()){
            view.findViewById<TextView>(R.id.temperature_field).text = "36.6"
        }else{
            view.findViewById<TextView>(R.id.temperature_field).text = temperature
        }
        if(temperature.toFloat() > 37.0f){
            view.findViewById<TextView>(R.id.temperature_field).setTextColor(Color.parseColor("#fd4949"))
        }
        if (time.isEmpty()){
            view.findViewById<TextView>(R.id.time_field).text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Calendar.getInstance().time)
        }else{
            view.findViewById<TextView>(R.id.time_field).text = time
        }
        temperat = Temperature(temperature.toFloat(), time)
        view.setOnClickListener {
            Toast.makeText(activity, "Удерживайте для удаления", Toast.LENGTH_SHORT).show()
        }
        view.setOnLongClickListener{
            view.visibility = View.GONE
            (activity as MainActivity).addReportFragment.report.temperats.remove(temperat)
            true
        }
        return view
    }
}
