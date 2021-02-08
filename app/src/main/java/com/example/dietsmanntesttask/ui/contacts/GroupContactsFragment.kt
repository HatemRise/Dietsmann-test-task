package com.example.dietsmanntesttask.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.ContactChangeFragment
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Contact

class GroupContactsFragment(val contacts: List<Contact>) : Fragment(), ContactListAdapter.OnItemClickListener {
    private val adapter = ContactListAdapter(contacts, this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_group_contacts, container, false)
        val contactList: RecyclerView = view.findViewById(R.id.contact_list)
        contactList.adapter = adapter
        contactList.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onItemClick(position: Int) {
        (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.contentPanel, ContactChangeFragment(contacts[position])).commit()
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(activity, "А для удаления мне не дали документации)", Toast.LENGTH_SHORT).show()
    }
}