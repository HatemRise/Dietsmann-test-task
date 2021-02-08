package com.example.dietsmanntesttask.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.ui.ListAdapter

class ListFragment : Fragment(), ListAdapter.OnItemClickListener {
    lateinit var reportList: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        reportList = view.findViewById(R.id.list)
        reportList.adapter = ListAdapter((activity as MainActivity).week, this)
        reportList.layoutManager = LinearLayoutManager(activity)
        (activity as MainActivity).findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.temperature).setBackgroundColor(
                Color.parseColor("#4685ff"))
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.temperature).setBackgroundColor(
                Color.WHITE)
    }

    override fun onItemClick(position: Int) {
        (activity as MainActivity).addReport(position)
    }
}