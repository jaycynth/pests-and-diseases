package com.julie.adchakathon.ui.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julie.adchakathon.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            when {
                TextUtils.isEmpty(username) -> {
                    binding.username.requestFocus()
                    binding.usernameLayout.error = "Enter username"
                }
                TextUtils.isEmpty(password) -> {
                    binding.password.requestFocus()
                    binding.passwordLayout.error = "Enter password"
                }

                else -> {
                }
            }
        }
    }
}