package com.example.dietsmanntesttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import com.example.dietsmanntesttask.entities.Contact
import com.example.dietsmanntesttask.ui.contacts.ContactsFragment

class ContactChangeFragment(val contact: Contact) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_change, container, false)
        val radio: RadioGroup = view.findViewById(R.id.change_contact_radiogroup)
        val name: EditText = view.findViewById(R.id.change_contact_name)
        when (contact.status) {
            "Болен" -> {radio.check(R.id.change_contact_status_false)}
            "Здоров" -> {radio.check(R.id.change_contact_status_true)}
        }
        name.setText(contact.fiocont)
        view.findViewById<AppCompatButton>(R.id.change_contact_cancel).setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.contentPanel, ContactsFragment()).commit()
        }
        view.findViewById<AppCompatButton>(R.id.change_contact_apply).setOnClickListener {
            contact.fiocont = name.text.toString()
            contact.status = view.findViewById<RadioButton>(radio.checkedRadioButtonId).text.toString()
            Thread {
                (activity as MainActivity).connection.changeContact(contact)
            }.start()
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.contentPanel, ContactsFragment()).commit()
        }
        return view
    }
}