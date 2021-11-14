package com.hana897trx.womenplustech.MyCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentMyCoursesBinding
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
}