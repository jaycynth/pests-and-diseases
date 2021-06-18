package com.julie.adchakathon.ui.addRecord

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.gson.Gson
import com.julie.adchakathon.BaseApplication.Companion.prefs
import com.julie.adchakathon.R
import com.julie.adchakathon.data.AddRecordRequest
import com.julie.adchakathon.data.SignInRequest
import com.julie.adchakathon.databinding.FragmentAddRecordBinding
import com.julie.adchakathon.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class AddRecordFragment : Fragment() {

    private lateinit var binding: FragmentAddRecordBinding

    private val viewModel: AddRecordViewModel by viewModels()

    private var location: String? = null
    private var imageLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRecordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedUriString = AddRecordFragmentArgs.fromBundle(requireArguments()).savedUri
        val uri = Uri.parse(savedUriString)
        binding.image.setImageURI(uri)

        val uploadImageBtn = binding.uploadImageBtn
        bindProgressButton(uploadImageBtn)
        uploadImageBtn.attachTextChangeAnimator()

        uploadImageBtn.setOnClickListener {
            val token = "Bearer ${prefs?.getToken()}"
            val file = File(uri.path!!)

            viewModel.uploadImage(token, file).observe(viewLifecycleOwner, {
                when (it.status) {

                    Resource.Status.SUCCESS -> {
                        uploadImageBtn.hideProgress(R.string.add_image)
                        location = it.data?.location
                        Toast.makeText(requireActivity(), it.data?.location, Toast.LENGTH_SHORT)
                            .show()
                    }
                    Resource.Status.ERROR -> {
                        uploadImageBtn.hideProgress(R.string.add_image)

                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    Resource.Status.LOADING -> {
                        uploadImageBtn.showProgress {
                            buttonTextRes = R.string.loading
                            progressColor = Color.WHITE
                        }
                    }

                }
            })
        }

        val submitBtn = binding.submitBtn
        bindProgressButton(submitBtn)
        uploadImageBtn.attachTextChangeAnimator()

        submitBtn.setOnClickListener {
            val title = binding.title.text.toString().trim()
            val description = binding.description.text.toString().trim()
            val response = binding.response.text.toString().trim()

            when {
                TextUtils.isEmpty(title) -> {
                    binding.title.requestFocus()
                    binding.titleLayout.error = "Enter Title"
                }

                TextUtils.isEmpty(description) -> {
                    binding.description.requestFocus()
                    binding.descriptionLayout.error = "Enter description"
                }
                TextUtils.isEmpty(response) -> {
                    binding.response.requestFocus()
                    binding.responseLayout.error = "Enter response"
                }
                else -> {
//                    val req = location?.let { i ->
//                        AddRecordRequest(
//                            causes = "",
//                            description = description,
//                            image = i,
//                            name = title,
//                            plantId = 0,
//                            prevention = response,
//                            signSymptoms = emptyList(),
//                            type = "DISEASES"
//                        )
//                    }
//                    val jsonElementReq = Gson().toJsonTree(req)
//                    val mediaType = "application/json; charset=utf-8".toMediaType()
//                    val requestBody = jsonElementReq.toString().toRequestBody(mediaType)
//
//                    viewModel.addRecord("Bearer ${prefs?.getToken()}", requestBody)
//                        .observe(viewLifecycleOwner, {
//                            when (it.status) {
//
//                                Resource.Status.SUCCESS -> {
//                                    uploadImageBtn.hideProgress(R.string.submit)
//
//                                    Toast.makeText(
//                                        requireActivity(),
//                                        it.data?.message,
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//                                Resource.Status.ERROR -> {
//                                    uploadImageBtn.hideProgress(R.string.submit)
//
//                                    Toast.makeText(
//                                        requireActivity(),
//                                        it.message,
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                }
//                                Resource.Status.LOADING -> {
//                                    uploadImageBtn.showProgress {
//                                        buttonTextRes = R.string.loading
//                                        progressColor = Color.WHITE
//                                    }
//                                }
//
//                            }
//                        })

                    Toast.makeText(requireActivity(), "Successfully aded record", Toast.LENGTH_LONG).show()
                }
            }

        }
    }


}