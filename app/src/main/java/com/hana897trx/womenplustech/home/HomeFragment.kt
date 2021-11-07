package com.hana897trx.womenplustech.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.hana897trx.womenplustech.model.Adapter.EventAdapter
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentHomeBinding
import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Observable.CampusDataUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fresco.initialize(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            categoriesObservable()
        }

        //spCampus()
        events()
    }

    private suspend fun categoriesObservable() {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            homeViewModel.campusDataUI.collect {
                when(it) {
                    is CampusDataUI.Success -> {spCampus(it.data)}
                    is CampusDataUI.Loading -> {}
                    is CampusDataUI.Error -> {}
                }
            }
        }

    }

    private fun spCampus(data : List<CampusEntity>) = binding.apply {
        val campusShort = arrayListOf<String>()
        for(campus in data) {
            campusShort.add(campus.campusShort)
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, campusShort)
        spCampus.adapter = adapter

        /*val data = homeViewModel?.getCampus()
        data?.observe(viewLifecycleOwner, {
            val campus = arrayListOf<String>()
            for(i in 0 until it.length()){
                campus.add(it[i].toString())
            }
            val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, campus)
            binding?.spCampus?.adapter = adapter
        })
         */
    }

    private fun events() {
        val data = homeViewModel.getEvents()
        data.observe(viewLifecycleOwner, {
            val adapter = EventAdapter(requireContext(), R.layout.event_layout, it)
            binding.rvEvents.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
            binding.rvEvents.adapter = adapter
        })
    }
}
