package com.hana897trx.womenplustech.accountCreation

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facebook.common.util.Hex
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.FragmentCreateAccountBinding
import com.hana897trx.womenplustech.model.Models.User
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class CreateAccountFragment : Fragment() {
    private lateinit var binding : FragmentCreateAccountBinding
    private var calendar = Calendar.getInstance()

    private var date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        binding.txtBirthday.setText("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backToLogin()
        registerAccount()
    }

    private fun backToLogin() = binding.apply {
        btnBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment2_to_login)
        }
    }

    private fun registerAccount() = binding.apply {
        val db = AppDB.getInstance(requireContext()).userDao()
        txtBirthday.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnRegister.setOnClickListener {
            val mail = txtEmail.text.toString()
            val imgBitmap = (imgProfile.drawable as BitmapDrawable).bitmap
            val fullName = txtName.text.toString()
            val birthday = txtBirthday.text.toString()
            val gender = spGender.selectedItem.toString()
            val password = txtPassword.text.toString()
            val confirmPassword = txtPasswordConfirm.text.toString()

            val steam = ByteArrayOutputStream()
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 90, steam)
            val format = SimpleDateFormat("yyyy-MM-dd")

            if(mail.isNotEmpty() && fullName.isNotEmpty() && birthday.isNotEmpty() && gender.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if(password == confirmPassword) {
                    lifecycleScope.launch(Dispatchers.IO) {

                        val register = db.getUser(mail)

                        if(register == null) {
                            val user = User(
                                id = 0,
                                userName = fullName,
                                birthDate = Date(format.parse(birthday).time),
                                email = mail,
                                gender = gender,
                                password = Hex.encodeHex(MessageDigest.getInstance("SHA-256").digest(password.toByteArray()), false),
                                imgProfile = steam.toByteArray()
                            )
                            db.newUser(user)

                            withContext(Dispatchers.Main){
                                Toast.makeText(
                                    requireContext(),
                                    R.string.user_registered,
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.action_createAccountFragment2_to_login)
                            }
                        }
                        else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.already_register,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}