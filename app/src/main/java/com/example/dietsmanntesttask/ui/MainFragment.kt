package com.example.dietsmanntesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R

class MainFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)
        view.findViewById<AppCompatButton>(R.id.contacts).setOnClickListener {
            (activity as MainActivity).contactsFragment()
        }
        view.findViewById<AppCompatButton>(R.id.temperature).setOnClickListener {
            (activity as MainActivity).temperatureFragment()
        }
        view.findViewById<AppCompatButton>(R.id.logout).setOnClickListener {
            (activity as MainActivity).logout()
        }
        view.findViewById<AppCompatButton>(R.id.add).setOnClickListener {
            (activity as MainActivity).addReport(null)
        }
        return view
    }
}