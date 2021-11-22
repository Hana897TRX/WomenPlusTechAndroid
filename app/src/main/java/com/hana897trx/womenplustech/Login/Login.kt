package com.hana897trx.womenplustech.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentLogInBinding
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Login : Fragment() {
    private lateinit var binding : FragmentLogInBinding
    private lateinit var viewModel : LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        goToAccountCreation()

        binding.apply {
            btnLogin.setOnClickListener {
                logIn()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                userObservable()
            }
        }
    }

    private fun userObservable() = lifecycleScope.launch {
        viewModel.userDataUI.collect {
            when(it) {
                is UserDataUI.Sucess -> { Toast.makeText(requireContext(), "Has iniciado sesion", Toast.LENGTH_SHORT).show() }
                is UserDataUI.Loading -> {}
                is UserDataUI.Error -> { Toast.makeText(requireContext(), R.string.wrong_user_or_password, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun goToAccountCreation() = binding.apply {
        btnGoToCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_createAccountFragment2)
        }
    }

    private fun logIn() = binding.apply {
        val email = txtLoginEmail.text.toString()
        val password = txtLoginPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()) {
            viewLifecycleOwner.lifecycleScope.launch { viewModel.checkLogin(email, password, cbRememberMe.isChecked) }
        }
    }
}