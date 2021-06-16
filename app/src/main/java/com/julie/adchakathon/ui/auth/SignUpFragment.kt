package com.julie.adchakathon.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.julie.adchakathon.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {

            val names = binding.username.text.toString().trim()
            val location = binding.location.text.toString().trim()
            val password = binding.password.text.toString().trim()

            when {
                TextUtils.isEmpty(names) -> {
                    binding.username.requestFocus()
                    binding.usernameLayout.error = "Enter Names"
                }
                TextUtils.isEmpty(location) -> {
                    binding.location.requestFocus()
                    binding.locationLayout.error = "Enter Location"
                }
                TextUtils.isEmpty(password) -> {
                    binding.password.requestFocus()
                    binding.passwordLayout.error = "Enter Password"
                }
                else -> {

                }
            }
        }

    }
}