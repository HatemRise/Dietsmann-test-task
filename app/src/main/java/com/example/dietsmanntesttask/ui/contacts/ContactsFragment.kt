package com.example.dietsmanntesttask.ui.contacts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R
import java.lang.Exception

class ContactsFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        (activity as MainActivity).findViewById<AppCompatButton>(R.id.contacts).setBackgroundColor(
                Color.parseColor("#4685ff"))

        view.findViewById<AppCompatButton>(R.id.work_contacts_button).setOnClickListener {
            (activity as MainActivity).contactsButton(1)
        }
        view.findViewById<AppCompatButton>(R.id.family_contacts_button).setOnClickListener {
            (activity as MainActivity).contactsButton(2)
        }
        view.findViewById<AppCompatButton>(R.id.friends_contacts_button).setOnClickListener {
            (activity as MainActivity).contactsButton(3)
        }
        view.findViewById<AppCompatButton>(R.id.other_contacts_button).setOnClickListener {
            (activity as MainActivity).contactsButton(4)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            (activity as MainActivity).findViewById<AppCompatButton>(R.id.contacts)
                .setBackgroundColor(
                    Color.WHITE
                )
        }catch (e: Exception){
            return
        }
    }
}