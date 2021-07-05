package com.hana897trx.womenplustech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hana897trx.womenplustech.Adapter.MyCoursesAdapter
import com.hana897trx.womenplustech.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCourses : Fragment() {

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
            val events = db.eventDao().getRegisterEvents()

            withContext(Dispatchers.Main) {
                val adapter = MyCoursesAdapter(view.context, R.layout.event_my_courses_layout, events)
                rvCourses.adapter = adapter
            }
        }
    }
}