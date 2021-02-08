package com.example.dietsmanntesttask.ui.contacts

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Contact

class ContactListAdapter(
    private val list: List<Contact>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ContactListAdapter.ContactListHolder>(){
    inner class ContactListHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener, View.OnLongClickListener{
        val contactName: TextView = itemView.findViewById(R.id.contact_name)
        val status: TextView = itemView.findViewById(R.id.contact_health_status)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onItemClick(position)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onItemLongClick(position)
            return true
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item_template,
            parent, false)
        return ContactListHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactListHolder, position: Int) {
        val currentItem = list[position]
        holder.contactName.text = currentItem.fiocont
        if (currentItem.status == "Болен"){
            holder.status.setBackgroundResource(R.drawable.status_background_false)
        }
        holder.status.text = currentItem.status
    }

    override fun getItemCount() = list.size
}