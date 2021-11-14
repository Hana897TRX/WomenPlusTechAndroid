package com.hana897trx.womenplustech.MyCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentMyCoursesBinding
import com.hana897trx.womenplustech.databinding.MyCurrentEventsBinding
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Adapter.MyCoursesAdapter
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCourses : Fragment() {
    private lateinit var binding : FragmentMyCoursesBinding
    private lateinit var viewModel : MyCoursesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MyCoursesViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                eventsObservable()
            }
        }
    }

    private suspend fun eventsObservable() {
        lifecycleScope.launch {
            viewModel.myCoursesDataUI.collect{
                when(it) {
                    is EventsDataUI.Success -> setEvents(it.data)
                    is EventsDataUI.Loading -> {}
                    is EventsDataUI.Error -> {}
                }
            }
        }
    }

    private fun setEvents(data : List<Event>) = binding.apply {
        rvMyCourses.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
        rvMyCourses.adapter = MyCoursesAdapter(root.context, R.layout.event_my_courses_layout, data)
    }

    /*
    private suspend fun loadMyCourses(view : View){
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
     */
}