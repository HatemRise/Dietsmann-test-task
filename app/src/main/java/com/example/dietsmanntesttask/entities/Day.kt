package com.example.dietsmanntesttask.entities

class Day {
    internal var success: Boolean = false
    internal lateinit var date: String
    internal lateinit var repid:String
    internal var temperat: Float = 0f
    internal lateinit var contacts: List<Contact>
}