package com.hana897trx.womenplustech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hana897trx.womenplustech.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment() {

    private lateinit var binding : FragmentCreateAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backToLogin()
    }

    private fun backToLogin() = binding.apply {
        btnBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment2_to_login)
        }
    }

}