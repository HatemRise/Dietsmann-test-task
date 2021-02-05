package com.example.dietsmanntesttask.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.findViewById<Button>(R.id.login).setOnClickListener {
            val login = view.findViewById<EditText>(R.id.username).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            when {
                login.isBlank() -> {
                    Toast.makeText(activity, "Введите логин", Toast.LENGTH_SHORT).show()
                }
                password.isBlank() -> {
                    Toast.makeText(activity, "Введите пароль", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    it.isEnabled = false
                    Thread {
                        val user = (activity as MainActivity).connection.login(login, password)
                        if (user != null) {
                            (activity as MainActivity).saveUser(user)
                        } else {
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    activity,
                                    "Не верный пароль, или логин",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                it.isEnabled = true
                                view.findViewById<EditText>(R.id.username).text.clear()
                                view.findViewById<EditText>(R.id.password).text.clear()
                            }
                        }
                    }.start()
                }
            }
        }
        return view
    }


}