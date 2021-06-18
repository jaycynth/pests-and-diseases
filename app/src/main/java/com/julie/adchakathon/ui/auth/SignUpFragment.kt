package com.julie.adchakathon.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.julie.adchakathon.R
import com.julie.adchakathon.data.SignUpRequest
import com.julie.adchakathon.databinding.FragmentSignUpBinding
import com.julie.adchakathon.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signInTxt.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        binding.signInBtn.setOnClickListener {

            val fName = binding.firstName.text.toString().trim()
            val lName = binding.lastName.text.toString().trim()
            val location = binding.location.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val phone = binding.phoneNumber.text.toString().trim()

            when {
                TextUtils.isEmpty(fName) -> {
                    binding.firstName.requestFocus()
                    binding.firstNameLayout.error = "Enter First Name"
                }
                TextUtils.isEmpty(lName) -> {
                    binding.lastName.requestFocus()
                    binding.lastNameLayout.error = "Enter Last Name"
                }
                TextUtils.isEmpty(username) -> {
                    binding.username.requestFocus()
                    binding.usernameLayout.error = "Enter Username"
                }
                TextUtils.isEmpty(phone) -> {
                    binding.phoneNumber.requestFocus()
                    binding.phoneNumberLayout.error = "Enter Phone number"
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
                    val req = SignUpRequest(
                            firstName = fName,
                            lastName = lName,
                            password = password,
                            location = location,
                            username = username,
                            phoneNumber = phone,
                            usertype = "USER"
                    )
                    val jsonElementReq = Gson().toJsonTree(req)
                    val mediaType = "application/json; charset=utf-8".toMediaType()
                    val requestBody = jsonElementReq.toString().toRequestBody(mediaType)

                    viewModel.register(requestBody).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                Toast.makeText(
                                        requireActivity(),
                                        it.data?.message,
                                        Toast.LENGTH_SHORT
                                ).show()

                                findNavController().navigate(R.id.signInFragment)
                            }
                            Resource.Status.ERROR -> {
                                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                                        .show()
                            }

                            Resource.Status.LOADING -> {
                                Toast.makeText(requireActivity(), "Loading...", Toast.LENGTH_SHORT)
                                        .show()
                            }

                        }
                    })
                }
            }
        }

    }
}