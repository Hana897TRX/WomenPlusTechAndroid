package com.hana897trx.womenplustech.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentUserProfileBinding
import com.hana897trx.womenplustech.model.Adapter.EventCardAdapter
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Models.User
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserProfileFragment : Fragment() {
    private lateinit var binding : FragmentUserProfileBinding
    private lateinit var userViewModel : userProfileViewModel
    //private var sharedPreference = requireContext().getSharedPreferences("remember-user", Context.MODE_PRIVATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(this).get(userProfileViewModel::class.java)

        val idUser = arguments?.getInt("idUser", 0)

        backToLogin()

        userViewModel.getUserData(idUser!!)
        userViewModel.getEventsUser(idUser)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                userObservable()
                eventsObservable()
            }
        }
    }

    private suspend fun eventsObservable() = lifecycleScope.launch {
        userViewModel._eventsDataUI.collect {
            when(it) {
                is EventsDataUI.Success -> { setEventsData(it.data) }
                is EventsDataUI.Error -> {  }
                is EventsDataUI.Loading -> {  }
            }
        }
    }

    private fun backToLogin() = binding.apply {
        /*sharedPreference.edit().apply {
            putString("password", "_")
            apply()
        }*/

        btnBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_login)
        }
    }

    private suspend fun userObservable() = lifecycleScope.launch {
        userViewModel._userDataUI.collect {
            when(it) {
                is UserDataUI.Sucess -> { setUserView(it.data) }
                is UserDataUI.Error -> { }
                is UserDataUI.Loading -> { }
            }
        }
    }

    private fun setEventsData(events : List<Event> ) = binding.apply {
        txtEventsTotal.text = events.size.toString()
        rvEvents.adapter = EventCardAdapter(requireContext(), R.layout.my_current_events, events)
        rvEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUserView(user : User) = binding.apply {
        txtName.text = user.userName
    }
}