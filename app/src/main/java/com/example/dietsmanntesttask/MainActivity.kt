package com.example.dietsmanntesttask

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dietsmanntesttask.connect.Connect
import com.example.dietsmanntesttask.connect.Connection
import com.example.dietsmanntesttask.entities.Day

class MainActivity : AppCompatActivity() {
    private lateinit var week: List<Day>
    private lateinit var connection: Connection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connection = Connect()
        Thread {
            connection.login("ivan_ivanov", "123456")
            week = connection.getWeek()!!
            println("Engaged")
        }.start()
        getSettings()

    }

    private fun getSettings(){
        val setting = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = setting.getString("token", null)
        val role = setting.getString("role", null)
        val name = setting.getString("name", null)
        if(token == null || name == null){
            //fragment login
        } else {
            connection.setUser(token, role ?: "User", name)
        }
    }
}