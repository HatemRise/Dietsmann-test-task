package com.example.dietsmanntesttask.ui.report_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.example.dietsmanntesttask.MainActivity
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Contact
import java.lang.Exception

class AddContactToReportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_contact_to_report, container, false)
        (activity as MainActivity).findViewById<AppCompatButton>(R.id.report_add_contact).isEnabled = false
        view.findViewById<AppCompatButton>(R.id.new_contact_cancel_button).setOnClickListener {
            (view.parent as ViewManager).removeView(view)
            (activity as MainActivity).findViewById<AppCompatButton>(R.id.report_add_contact).isEnabled = true
        }
        val fio: EditText = view.findViewById(R.id.new_contact_fio)
        val placecont: EditText = view.findViewById(R.id.new_contact_placecont)
        fio.requestFocus()
        view.findViewById<AppCompatButton>(R.id.new_contact_add_button).setOnClickListener {
            val radioButton: RadioButton = view.findViewById(view.findViewById<RadioGroup>(R.id.radio_button_group).checkedRadioButtonId)
            if(isEmptyFields(fio, placecont)){
                Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }else {
                var group: Short = 1
                when {
                    radioButton.text.equals("Работа") -> {
                        group = 1
                    }
                    radioButton.text.equals("Семья") -> {
                        group = 2
                    }
                    radioButton.text.equals("Друзья") -> {
                        group = 3
                    }
                    radioButton.text.equals("Другие") -> {
                        group = 4
                    }
                }
                (activity as MainActivity).addReportFragment.report.contacts.add(Contact("", placecont.text.toString(), fio.text.toString(), "", group))
                (activity as MainActivity).addReportFragment.adapter.notifyDataSetChanged()
                (view.parent as ViewManager).removeView(view)
                (activity as MainActivity).findViewById<AppCompatButton>(R.id.report_add_contact).isEnabled = true
            }
        }
        return view
    }

    private fun isEmptyFields(fio: TextView, placecont: TextView): Boolean {
        when{
            fio.text.isBlank() -> {
                return true
            }
            placecont.text.isBlank() -> {
                return true
            }
        }
        return false
    }
}