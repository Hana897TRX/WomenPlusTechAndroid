package com.hana897trx.womenplustech.model.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hana897trx.womenplustech.databinding.MyCurrentEventsBinding
import com.hana897trx.womenplustech.model.Models.Event

class EventCardAdapter(private val context: Context,
                       private val layout: Int,
                       private val dataSource: List<Event> ) :
    RecyclerView.Adapter<EventCardAdapter.ClassViewHolder>() {

    class ClassViewHolder(val binding: MyCurrentEventsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data : Event) = binding.apply {
            val uri = Uri.parse(data.eventImage)
            txtEventName.text = data.title
            txtEventState.text = data.eventType
            txtSesions.text = data.schedule
            imgEvent.setImageURI(uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val binding = MyCurrentEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val data = dataSource[position]
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}