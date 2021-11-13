package com.hana897trx.womenplustech.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentLogInBinding

class Login : Fragment() {
    private lateinit var binding : FragmentLogInBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        goToAccountCreation()
    }

    private fun goToAccountCreation() = binding.apply {
        btnGoToCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_createAccountFragment2)
        }
    }
}