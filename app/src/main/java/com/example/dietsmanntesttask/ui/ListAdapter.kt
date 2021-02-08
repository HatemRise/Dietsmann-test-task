package com.example.dietsmanntesttask.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Day
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ListAdapter(
        private val list: List<Day>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    inner class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val date: TextView = itemView.findViewById(R.id.date_field)
        val contacts: TextView = itemView.findViewById(R.id.contact_field)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onItemClick(position)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_template,
                parent, false)
        return ListHolder(itemView)
    }

    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val currentItem = list[position]
        holder.date.text = SimpleDateFormat("dd MMMM", Locale.getDefault())
            .format(SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm",
                Locale.getDefault())
                .parse(currentItem.date))
        holder.contacts.text = "Контактов: ${currentItem.contacts.size}"
    }
}