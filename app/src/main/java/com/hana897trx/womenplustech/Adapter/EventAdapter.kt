package com.hana897trx.womenplustech.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.hana897trx.womenplustech.Models.Event
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.EventInfo


class EventAdapter(private val context: Context,
                   private val layout: Int,
                   private val dataSource: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

      class EventViewHolder(inflater: LayoutInflater,
                            parent: ViewGroup,
                            layout: Int,
                            context: Context) : RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)) {
          var imgCover: SimpleDraweeView? = null
          var txtTitle: TextView? = null
          var txtCampus: TextView? = null
          var txtSchedule: TextView? = null
          var txtEventType : TextView? = null
          var cardEvent : CardView? = null
          var context : Context

          init {
              imgCover  = itemView.findViewById(R.id.imgCourse)
              txtTitle = itemView.findViewById(R.id.txtCourseTitle)
              txtCampus = itemView.findViewById(R.id.txtEventType)
              txtSchedule = itemView.findViewById(R.id.txtSchedule)
              cardEvent = itemView.findViewById(R.id.cardEvent)
              txtEventType = itemView.findViewById(R.id.txtEventType)
              this.context = context
          }

          fun bindData(event: Event){
              val url = Uri.parse(event.eventImage)
              imgCover!!.setImageURI(url)
              txtTitle!!.text = event.title
              txtCampus!!.text = event.campus
              txtSchedule!!.text = event.days
              txtEventType!!.text = event.eventType

              cardEvent!!.setOnClickListener {
                  val i = Intent(context, EventInfo::class.java).apply {
                      putExtra("id", event.id)
                      putExtra("title", event.title)
                      putExtra("campus", event.campus)
                      putExtra("description", event.description)
                      putExtra("days", event.days)
                      putExtra("schedule", event.schedule)
                      putExtra("requirements", event.requirements)
                      putExtra("registerLink", event.registerLink)
                      putExtra("temary", event.temary)
                      putExtra("eventType", event.eventType)
                      putExtra("eventImage", event.eventImage)
                      putExtra("fechaInicio", event.fechaInicio.toString())
                  }
                  context.startActivity(i)
              }
          }
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(inflater, parent, layout, parent.context)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val elemento = dataSource[position]
        holder.bindData(elemento)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}