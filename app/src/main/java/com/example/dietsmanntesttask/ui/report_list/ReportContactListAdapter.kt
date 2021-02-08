package com.example.dietsmanntesttask.ui.report_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietsmanntesttask.R
import com.example.dietsmanntesttask.entities.Contact

class ReportContactListAdapter(
    private val list: List<Contact>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ReportContactListAdapter.ReportContactListHolder>() {
    inner class ReportContactListHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener{
        val fio: TextView = itemView.findViewById(R.id.contact_fio)
        val placecont: TextView= itemView.findViewById(R.id.contact_placecont)
        val group: TextView = itemView.findViewById(R.id.contact_group)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportContactListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.report_contact_list_item,
            parent, false)
        return ReportContactListHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReportContactListHolder, position: Int) {
        val item = list[position]
        holder.fio.text = item.fiocont
        holder.placecont.text = item.placecont
        when{
            item.group == 1.toShort() ->{
                holder.group.text = "Работа"
            }
            item.group == 2.toShort() ->{
                holder.group.text = "Семья"
            }
            item.group == 3.toShort() ->{
                holder.group.text = "Друзья"
            }
            item.group == 4.toShort() ->{
                holder.group.text = "Другие"
            }
        }
    }

    override fun getItemCount() = list.size
    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
}