package com.hana897trx.womenplustech.myCourses

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.model.Utility.AppDB
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
                val bundle = bundleOf("idEvent" to event.id)
                it.findNavController().navigate(R.id.action_myCourses_to_myCoursesMessagesFragment, bundle)
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