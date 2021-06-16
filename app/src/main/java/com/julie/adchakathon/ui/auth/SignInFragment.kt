package com.julie.adchakathon.ui.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.julie.adchakathon.BaseApplication.Companion.prefs
import com.julie.adchakathon.R
import com.julie.adchakathon.data.SignInRequest
import com.julie.adchakathon.databinding.FragmentSignInBinding
import com.julie.adchakathon.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

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

                    val req = SignInRequest(password = password, username = username)
                    val jsonElementReq = Gson().toJsonTree(req)
                    val mediaType = "application/json; charset=utf-8".toMediaType()
                    val requestBody = jsonElementReq.toString().toRequestBody(mediaType)

                    viewModel.login(requestBody).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                val token = it.data?.accessToken
                                token?.let { t -> prefs?.setToken(t) }
                                findNavController().navigate(R.id.homeFragment)
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