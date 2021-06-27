package com.hana897trx.womenplustech.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.hana897trx.womenplustech.Models.Event
import com.hana897trx.womenplustech.R
import java.net.URL


class EventAdapter(private val context: Context,
                   private val layout: Int,
                   private val dataSource: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

      class EventViewHolder(inflater: LayoutInflater,
                            parent: ViewGroup,
                            layout: Int) : RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)) {
          var imgCover: SimpleDraweeView? = null
          var txtTitle: TextView? = null
          var txtCampus: TextView? = null
          var txtSchedule: TextView? = null

          init {
              imgCover  = itemView.findViewById(R.id.imgCourse)
              txtTitle = itemView.findViewById(R.id.txtCourseTitle)
              txtCampus = itemView.findViewById(R.id.txtCampus)
              txtSchedule = itemView.findViewById(R.id.txtSchedule)
          }

          fun bindData(event: Event){
              val url = Uri.parse(event.eventImage)
              imgCover!!.setImageURI(url)



              txtTitle!!.text = event.title
              txtCampus!!.text = event.campus
              txtSchedule!!.text = event.days
          }
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(inflater, parent, layout)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val elemento = dataSource[position]
        holder.bindData(elemento)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}