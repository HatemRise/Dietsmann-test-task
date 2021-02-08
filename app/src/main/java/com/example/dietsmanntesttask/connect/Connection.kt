package com.example.dietsmanntesttask.connect

import android.text.Editable
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.entities.Contact
import com.example.dietsmanntesttask.entities.Day
import com.example.dietsmanntesttask.entities.User

interface Connection {
    fun setUser(token: String, role: String, name: String)
    fun login(login: String, password: String) : User?
    fun getWeek() : List<Day>?
    fun save(day: Day)
    fun getGroupContacts(num: Short) : List<Contact>
    fun changeContact(contact: Contact)
}