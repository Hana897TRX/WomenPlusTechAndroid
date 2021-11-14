package com.hana897trx.womenplustech.MyCourses.Messages

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
import com.hana897trx.womenplustech.databinding.FragmentMyCoursesMessagesBinding
import com.hana897trx.womenplustech.model.Models.Messages
import com.hana897trx.womenplustech.model.Observable.MyMessagesDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyCoursesMessagesFragment : Fragment() {
    private lateinit var binding : FragmentMyCoursesMessagesBinding
    private lateinit var viewModel : MessagesViewModel
    private var idEvent = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyCoursesMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idEvent = arguments?.getString("idEvent", "0")!!
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        viewModel.getMessages(idEvent)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                messagesObservable()
                setAsSeen()
            }
        }
        setBackBtn()
    }

    private suspend fun messagesObservable() {
        lifecycleScope.launch {
            viewModel.messagesDataUI.collect {
                when(it) {
                    is MyMessagesDataUI.Success -> setMessages(it.data)
                    is MyMessagesDataUI.Loading -> { /* NOTHING TO DO*/}
                    is MyMessagesDataUI.Error -> { }
                }
            }
        }
    }

    private fun setBackBtn() = binding.apply {
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_myCoursesMessagesFragment_to_myCourses)
        }
    }

    private fun setMessages(data : List<Messages>) = binding.apply{
        rvMessages.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, true)
        rvMessages.adapter = MessageAdapter(root.context, R.layout.message_layout, data)
    }

    private fun setAsSeen() = lifecycleScope.launch(Dispatchers.IO) {
        val db = AppDB.getInstance(requireContext())
        db.messageDao().updateSeenAt(idEvent)
    }
}