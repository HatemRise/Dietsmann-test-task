package com.example.dietsmanntesttask.connect

import androidx.annotation.WorkerThread
import com.example.dietsmanntesttask.entities.Contact
import com.example.dietsmanntesttask.entities.Day
import com.example.dietsmanntesttask.entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Connect : Connection{
    private lateinit var user: User

    @WorkerThread
    override fun login(login:String, password:String) : User? {
        val httpURLConnection = URL("${SERVER}/Auth").openConnection() as HttpURLConnection
        httpURLConnection.apply {
            connectTimeout = 5000
            requestMethod = "POST"
            doInput = true
            doOutput = true
            setRequestProperty("Content-type", "application/json")
        }
        val body = JSONObject().apply {
            put("Password", password)
            put("Username", login)
        }
        try {
            OutputStreamWriter(httpURLConnection.outputStream).use {
                it.write(body.toString())
            }
            if(httpURLConnection.responseCode == HttpURLConnection.HTTP_OK){
                val response = InputStreamReader(httpURLConnection.inputStream)
                var data = ""
                response.use {
                    data = it.readText()
                }
                user = Gson().fromJson(data, User::class.java)
            }
        }finally {
            httpURLConnection.disconnect()
        }
        return null
    }

    override fun setUser(token: String, role: String, name: String){
        user.empid = token
        user.role = role
        user.fio = name
    }

    @WorkerThread
    override fun getWeek() : List<Day>? {
        val httpURLConnection = URL("${SERVER}/Repconts/${user.empid}").openConnection() as HttpURLConnection
        httpURLConnection.apply {
            connectTimeout = 5000
            requestMethod = "GET"
            doInput = true
        }
        try {
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = InputStreamReader(httpURLConnection.inputStream)
                var data = ""
                response.use {
                    data = it.readText()
                }
                return Gson().fromJson(data, object : TypeToken<List<Day>>() {}.type)
            }
        }finally {
            httpURLConnection.disconnect()
        }
        return listOf()
    }

    override fun save(day: Day) {
        TODO("Not yet implemented")
    }

    override fun getGroupContacts(num: Short) : List<Contact>{
        val httpURLConnection = URL("${SERVER}/Spisconts").openConnection() as HttpURLConnection
        httpURLConnection.apply {
            connectTimeout = 5000
            requestMethod = "POST"
            doInput = true
            doOutput = true
            setRequestProperty("Content-type", "application/json")
        }
        val body = JSONObject().apply {
            put("Empid", user.empid)
            put("group", num)
        }
        OutputStreamWriter(httpURLConnection.outputStream).use {
            it.write(body.toString())
        }
        if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = InputStreamReader(httpURLConnection.inputStream)
            var data = ""
            response.use {
                data = it.readText()
            }
            return Gson().fromJson(data, object : TypeToken<List<Contact>>() {}.type)
        }
        return listOf()
    }

    override fun changeContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val SERVER = "https://timesur.ru/api"
    }
}