package com.hana897trx.womenplustech.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Adapter.MyCoursesAdapter
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCourses : Fragment() {
    private var apiMessages = APIMessages()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_courses, container, false)
        
        loadMyCourses(view)

        return view
    }

    private fun loadMyCourses(view : View){
        val rvCourses = view.findViewById<RecyclerView>(R.id.rvMyCourses)
        val db = AppDB.getInstance(requireContext())

        rvCourses.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch(Dispatchers.IO) {
            val events : List<Event> = db.eventDao().getRegisterEvents()

            withContext(Dispatchers.Main) {
                val adapter = MyCoursesAdapter(view.context,
                    R.layout.event_my_courses_layout, events)
                rvCourses.adapter = adapter
            }

            for(e in events) {
                lifecycleScope.launch(Dispatchers.Main) {
                    val messages = apiMessages.getEventMessages(e.id)
                    messages.observe(viewLifecycleOwner, {
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.messageDao().insertMessages(it)
                        }
                    })
                }
            }
        }
    }
}