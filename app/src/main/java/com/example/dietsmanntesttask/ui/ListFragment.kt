package com.example.dietsmanntesttask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.R

class ListFragment : Fragment() {
    lateinit var reportList: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        reportList = view.findViewById(R.id.list)
        reportList.layoutManager = LinearLayoutManager(activity)

        return view
    }
}