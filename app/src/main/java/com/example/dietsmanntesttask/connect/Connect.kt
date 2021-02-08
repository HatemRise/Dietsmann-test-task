package com.example.dietsmanntesttask.connect

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.WorkerThread
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.entities.Contact
import com.example.dietsmanntesttask.entities.Day
import com.example.dietsmanntesttask.entities.Temperature
import com.example.dietsmanntesttask.entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Connect(private var activity: MainActivity) : Connection{
    private lateinit var user: User

    @WorkerThread
    override fun login(login: String, password: String) : User? {
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
                it.flush()
            }
            if(httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = InputStreamReader(httpURLConnection.inputStream)
                var data: String
                response.use {
                    data = it.readText()
                }
                if (data != "{\"success\":false}"){
                    user = Gson().fromJson(data, User::class.java)
                    return user
                }
            }
        }finally {
            try {
                httpURLConnection.disconnect()
            }catch (e: Exception) {
                errorMassageThrow()
                return null
            }
        }
        return null
    }

    override fun setUser(token: String, role: String, name: String){
        user = User(name, token, role)
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
                var data: String
                response.use {
                    data = it.readText()
                }
                print(data)
                return Gson().fromJson(data, object : TypeToken<List<Day>>() {}.type)
            }
        }finally {
            try {
                httpURLConnection.disconnect()
            }catch (e: Exception) {
                errorMassageThrow()
                return null
            }
        }
        return null
    }

    @WorkerThread
    override fun save(day: Day) {
        val body = JSONObject().apply {
            put("empid", user.empid)
            put("temperats", JSONArray(Gson().toJson(listOf<Temperature>(Temperature(36.6f, "2021-02-08T04:23")))))
            put("Repcontdate", day.date)
            put("Comment", "Comment tests")
            put("Contacts", JSONArray(Gson().toJson(day.contacts)))
        }
        val httpURLConnection = URL("${SERVER}/Repconts").openConnection() as HttpURLConnection
        httpURLConnection.apply {
            connectTimeout = 5000
            requestMethod = "POST"
            doOutput = true
            doInput = true
            setRequestProperty("Content-type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
//            setRequestProperty("Content-length", body.toString().length.toString())
        }
        try {
            OutputStreamWriter(httpURLConnection.outputStream).use {
                it.write(body.toString())
            }
        }finally {
            try {
                httpURLConnection.disconnect()
            }catch (e: Exception) {
                errorMassageThrow()
            }
        }
        httpURLConnection.disconnect()
    }

    @WorkerThread
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
        try {
            OutputStreamWriter(httpURLConnection.outputStream).use {
                it.write(body.toString())
            }
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = InputStreamReader(httpURLConnection.inputStream)
                var data: String
                response.use {
                    data = it.readText()
                }
                return Gson().fromJson(JSONObject(data).get("data").toString(),
                    object : TypeToken<List<Contact>>() {}.type)
            }
        }finally {
            try {
                httpURLConnection.disconnect()
            }catch (e: Exception) {
                errorMassageThrow()
            }
        }
        return listOf()
    }

    @WorkerThread
    override fun changeContact(contact: Contact) {
        val httpURLConnection = URL("${SERVER}/Conts").openConnection() as HttpURLConnection
        httpURLConnection.apply {
            connectTimeout = 5000
            requestMethod = "PUT"
            doInput = true
            doOutput = true
            setRequestProperty("Content-type", "application/json")
        }
        val body = JSONObject().apply {
            put("Empid", user.empid)
            put("contid", contact.contid)
            put("fiocont", contact.fiocont)
            put("group", contact.group)
            put("status", contact.status)
        }
        try{
            OutputStreamWriter(httpURLConnection.outputStream).use {
                it.write(body.toString())
            }
            httpURLConnection.responseCode
        }finally {
            try {
                httpURLConnection.disconnect()
            }catch (e: Exception) {
                errorMassageThrow()
            }
        }
    }

    private fun errorMassageThrow(){
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                    activity,
                    "Сервер недоступен. Попробуйте позже",
                    Toast.LENGTH_LONG
            )
                    .show()
        }
    }

    companion object {
        private const val SERVER = "https://timesur.ru/api"
    }
}