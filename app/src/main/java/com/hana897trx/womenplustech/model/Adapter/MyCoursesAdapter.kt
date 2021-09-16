package com.hana897trx.womenplustech.model.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.model.Utility.AppDB
import com.hana897trx.womenplustech.view.myCoursesMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCoursesAdapter( private val context: Context,
                        private val layout: Int,
                        private val dataSource: List<Event>) : RecyclerView.Adapter<MyCoursesAdapter.CourseViewHolder>() {

    class CourseViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layout: Int,
        context: Context
    ) : RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)) {

        var imgCourse: SimpleDraweeView? = null
        var txtCourseTitle: TextView? = null
        var txtCourseDescription: TextView? = null
        var txtNotification: TextView? = null
        var cardCourseNotification: CardView? = null
        var cardCourseMessage: CardView? = null
        var context : Context? = null

        init {
            imgCourse = itemView.findViewById(R.id.imgCourse)
            txtCourseTitle = itemView.findViewById(R.id.txtCourseTitle)
            txtCourseDescription = itemView.findViewById(R.id.txtCourseDescription)
            txtNotification = itemView.findViewById(R.id.txtNotification)
            cardCourseNotification = itemView.findViewById(R.id.cardCourseNotification)
            cardCourseMessage = itemView.findViewById(R.id.cardCourseMessage)
            this.context = context
        }

        fun bindData(event: Event) {
            val url = Uri.parse(event.eventImage)
            imgCourse!!.setImageURI(url)
            txtCourseTitle!!.text = event.title
            txtCourseDescription!!.text = event.description

            GlobalScope.launch(Dispatchers.IO) {
                val db = AppDB.getInstance(context!!)
                val notSeen = db.messageDao().countNotSeen(event.id)

                withContext(Dispatchers.Main) {
                    txtNotification?.text = notSeen.toString()
                }
            }

            cardCourseMessage!!.setOnClickListener {
                val i = Intent(context, myCoursesMessages::class.java)
                i.putExtra("_id", event.id)
                txtNotification?.text = "0"

                context!!.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CourseViewHolder(inflater, parent, layout, parent.context)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val elemento = dataSource[position]
        holder.bindData(elemento)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}