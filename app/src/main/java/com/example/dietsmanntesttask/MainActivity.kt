package com.example.dietsmanntesttask

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dietsmanntesttask.connect.Connect
import com.example.dietsmanntesttask.connect.Connection
import com.example.dietsmanntesttask.entities.Day
import com.example.dietsmanntesttask.entities.User
import com.example.dietsmanntesttask.ui.login.LoginFragment
import com.example.dietsmanntesttask.ui.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var week: List<Day>
    lateinit var connection: Connection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContentView(R.layout.activity_main)
        connection = Connect()
        getSettings()
    }

    private fun getSettings(){
        val setting = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = setting.getString("token", null)
        val role = setting.getString("role", null)
        val name = setting.getString("name", null)
        if(token == null || name == null){
            supportFragmentManager.beginTransaction().replace(R.id.main_container, LoginFragment()).commit()
        } else {
            connection.setUser(token, role ?: "User", name)
            starMainScreen()
        }
    }

    fun saveUser(user:User){
        val setting = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        setting.edit().apply {
            this.putString("token", user.empid)
            this.putString("role", user.role)
            this.putString("name", user.fio)
        }.apply()
        starMainScreen()
    }

    private fun starMainScreen(){
        Thread {
            week = connection.getWeek()!!
        }.start()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MainFragment()).commit()
    }
}