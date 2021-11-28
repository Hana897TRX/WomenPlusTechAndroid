package com.hana897trx.womenplustech.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentLogInBinding
import com.hana897trx.womenplustech.model.Models.User
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    private suspend fun userObservable() {
        viewModel.userDataUI.collect {
            when (it) {
                is UserDataUI.Sucess -> {
                    goToAccountInfo(it.data)
                }
                is UserDataUI.Loading -> {
                }
                is UserDataUI.Error -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.wrong_user_or_password,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun goToAccountInfo(user: User) {
        val bundle = bundleOf("idUser" to user.id)
        findNavController().navigate(R.id.action_login_to_userProfileFragment2, bundle)
    }

    private fun goToAccountCreation() = binding.apply {
        btnGoToCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_createAccountFragment2)
        }
    }

    private fun logIn() = binding.apply {
        val email = txtLoginEmail.text.toString()
        val password = txtLoginPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.checkLogin(
                    email,
                    password,
                    cbRememberMe.isChecked,
                    false
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryLogin()
    }
}