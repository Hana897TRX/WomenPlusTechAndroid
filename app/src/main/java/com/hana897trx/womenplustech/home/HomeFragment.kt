package com.hana897trx.womenplustech.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.hana897trx.womenplustech.model.Adapter.EventAdapter
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentHomeBinding
import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Models.User
import com.hana897trx.womenplustech.model.Observable.CampusDataUI
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        goToAccount()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                categoriesObservable()
                eventsObservable()
                userObservable()
            }
        }
    }

    private suspend fun userObservable() {
        lifecycleScope.launch {
            homeViewModel.userDataUI.collect {
                when(it) {
                    is UserDataUI.Sucess -> {
                        binding.txtUsername.text = it.data.userName!!.split(" ")[0]

                        if(it.data.gender == resources.getStringArray(R.array.gender)[0])
                            binding.txtWelcome.text = resources.getStringArray(R.array.welcome)[0]
                        else if(it.data.gender == resources.getStringArray(R.array.gender)[1])
                            binding.txtWelcome.text = resources.getStringArray(R.array.welcome)[1]
                        else if(it.data.gender == resources.getStringArray(R.array.gender)[2])
                            binding.txtWelcome.text = resources.getStringArray(R.array.welcome)[2]
                        else if (it.data.gender == resources.getStringArray(R.array.gender)[3])
                            binding.txtWelcome.text = resources.getStringArray(R.array.welcome)[3]
                    }
                    is UserDataUI.Loading -> { }
                    is UserDataUI.Error -> {
                        binding.txtUsername.text = getString(R.string.guest)
                    }
                }
            }
        }
    }

    private suspend fun categoriesObservable() {
        lifecycleScope.launch {
            homeViewModel.campusDataUI.collect {
                when(it) {
                    is CampusDataUI.Success -> spCampus(it.data)
                    is CampusDataUI.Loading -> {}
                    is CampusDataUI.Error -> {}
                }
            }
        }
    }

    private suspend fun eventsObservable() {
        lifecycleScope.launch {
            homeViewModel.eventsUIState.collect {
                when(it){
                    is EventsDataUI.Success -> setEvents(it.data)
                    is EventsDataUI.Loading -> {}
                    is EventsDataUI.Error -> {}
                }
            }
        }
    }

    private fun goToAccount() = binding.apply {
        btnAccount.setOnClickListener {
            findNavController().navigate(R.id.myAccount)
        }
    }

    private fun spCampus(data : List<CampusEntity>) = binding.apply {
        val campusShort = arrayListOf<String>()
        for(campus in data) {
            campusShort.add(campus.campusName!!)
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, campusShort)
        spCampus.adapter = adapter
    }

    private fun setEvents(data : List<Event>) = binding.apply {
        val adapter = EventAdapter(requireContext(), R.layout.event_layout, data)
        rvEvents.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        rvEvents.adapter = adapter
    }
}
