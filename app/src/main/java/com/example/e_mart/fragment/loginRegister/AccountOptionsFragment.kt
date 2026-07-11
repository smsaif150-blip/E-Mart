package com.example.e_mart.fragment.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_mart.R
import com.example.e_mart.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment: Fragment() {
    private lateinit var binding: FragmentAccountOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegisterAppcompatOption.setOnClickListener {
          findNavController().navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }
        binding.buttonLoginAppcompatOption.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }
    }
}