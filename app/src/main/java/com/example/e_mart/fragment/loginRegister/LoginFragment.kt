package com.example.e_mart.fragment.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.flagging.Flags
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.e_mart.Activities.ShoppingActivity
import com.example.e_mart.R
import com.example.e_mart.databinding.FragmentLoginBinding
import com.example.e_mart.util.Resource
import com.example.e_mart.viewmodel.LoginViewModel
import kotlin.getValue

class LoginFragment: Fragment(R.layout.fragment_login) {
    val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonLoginLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString()
            viewModel.login(email,password)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect { result->
                when(result)
                {
                    is Resource.Loading<*> -> {
                        binding.LoginProgressbar.visibility = View.VISIBLE
                    }
                    is Resource.Success<*> -> {
                        binding.LoginProgressbar.visibility = View.GONE
                        val intent = Intent(requireContext(), ShoppingActivity::class.java)
                        intent.addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        )

                        startActivity(intent)
                    }
                    is Resource.Error<*>->{
                        binding.LoginProgressbar.visibility = View.GONE
                        Toast.makeText(requireContext(),result.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Test<*> -> TODO()
                }
            }
        }

    }
}