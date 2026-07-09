package com.example.e_mart.fragment.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.e_mart.R
import com.example.e_mart.data.User
import com.example.e_mart.databinding.FragmentRegisterBinding
import com.example.e_mart.util.RegisterValidation
import com.example.e_mart.util.Resource
import com.example.e_mart.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.getValue
@AndroidEntryPoint
class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    val viewmodel by viewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentRegisterBinding.inflate(layoutInflater)
       return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRegister.setOnClickListener {
            val user = User(
                binding.etFirstName.text.toString().trim(),
                binding.etLastName.text.toString().trim(),
                binding.etEmailToRegister.text.toString().trim()
            )

            val password = binding.etPasswordToRegister.text.toString()

            viewmodel.createAccountWithEmailAndPassword(user,password)


        }

        lifecycleScope.launchWhenStarted {
            viewmodel.register.collect {
                when(it)
                {
                    is Resource.Loading<*> -> {

                        binding.progressId.visibility = View.VISIBLE

                    }

                    is Resource.Success<*> -> {
                        binding.progressId.visibility = View.GONE
                        Log.d("TAG",it.data.toString())
                    }
                    is Resource.Error<*> -> {
                        binding.progressId.visibility = View.GONE
                        Log.d("TAG",it.message.toString())
                    }
                    is Resource.Test<*> -> {

                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.validation.collect { validation->
                if (validation.Email is RegisterValidation.Failure)
                {
                    withContext(Dispatchers.Main){
                        binding.etEmailToRegister.apply {
                            requestFocus()
                            error = validation.Email.message
                        }
                    }
                }
                else if(validation.Password is RegisterValidation.Failure)
                {
                    withContext(Dispatchers.Main)
                    {
                        binding.etPasswordToRegister.apply {
                            requestFocus()
                            error = validation.Password.message
                        }
                    }
                }
            }
        }

    }
}