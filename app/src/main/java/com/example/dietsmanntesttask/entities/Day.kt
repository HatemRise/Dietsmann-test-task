package com.example.dietsmanntesttask.entities

data class Day(
        var success: Boolean,
        var date: String,
        var repid: String,
        var temperats: ArrayList<Temperature>,
        var contacts: ArrayList<Contact>
)