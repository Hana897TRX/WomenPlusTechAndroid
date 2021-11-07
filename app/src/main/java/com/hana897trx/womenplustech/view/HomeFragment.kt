package com.hana897trx.womenplustech.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Adapter.EventAdapter
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentHomeBinding
import com.hana897trx.womenplustech.model.Utility.AppDB
import com.hana897trx.womenplustech.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date

class HomeFragment : Fragment() {
    private var fragmentHomeBinding : FragmentHomeBinding? = null
    private var homeViewModel : HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fresco.initialize(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        fragmentHomeBinding?.model = homeViewModel

        spCampus()
        events()
    }

    private fun spCampus() {
        val data = homeViewModel?.getCampus()
        data?.observe(viewLifecycleOwner, {
            val campus = arrayListOf<String>()
            for(i in 0 until it.length()){
                campus.add(it[i].toString())
            }
            val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, campus)
            fragmentHomeBinding?.spCampus?.adapter = adapter
        })
    }

    private fun events() {
        val data = homeViewModel?.getEvents()
        data?.observe(viewLifecycleOwner, {
            val adapter = EventAdapter(requireContext(), R.layout.event_layout, it)
            //fragmentHomeBinding?.rvEvents?.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
            fragmentHomeBinding?.rvEvents?.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            fragmentHomeBinding?.rvEvents?.adapter = adapter
        })
    }
}
