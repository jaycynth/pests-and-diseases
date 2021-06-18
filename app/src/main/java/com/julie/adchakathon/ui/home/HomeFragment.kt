package com.julie.adchakathon.ui.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.julie.adchakathon.BaseApplication.Companion.prefs
import com.julie.adchakathon.R
import com.julie.adchakathon.databinding.FragmentHomeBinding
import com.julie.adchakathon.ml.Model
import com.julie.adchakathon.utils.Constants.FILENAME_FORMAT
import com.julie.adchakathon.utils.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: FragmentHomeBinding
    private lateinit var labelList: List<String>
    private var savedUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (prefs?.getToken() == "null") {
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }

        binding.moreInfo.movementMethod = LinkMovementMethod.getInstance();


        binding.titleName.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        binding.getSolutionBtn.setOnClickListener {
            when {
                binding.solutionText.text.toString().trim() == "healthy" -> {
                    binding.solutionDescriptionText.text = "Your crop is healthy"
                }
                binding.solutionText.text.toString().trim() == "common rust" -> {
                    binding.solutionDescriptionText.text =
                        "Use either of the products; garden fungicides,liquid copper or orchid spray"
                }
                binding.solutionText.text.toString().trim() == "blight" -> {
                    binding.solutionDescriptionText.text =
                        "Prune or stake plants to improve air circulation and reduce fungal problems"
                }
                binding.solutionText.text.toString().trim() == "grey leafy spot" -> {
                    binding.solutionDescriptionText.text =
                        "Use either of the products; garden fungicides,liquid copper or orchid spray"
                }
            }
        }

        binding.addNewRecordBtn.setOnClickListener {
            if (savedUri != null) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToAddRecordFragment(savedUri.toString())
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    requireActivity(), "Take photo first before proceeding to add",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.titleName.text = "Hello, ${prefs?.getNames()}"

        val fileName = "label.txt"
        val inputString = requireActivity().application.assets.open(fileName).bufferedReader()
            .use { it.readText() }
        labelList = inputString.split("\n")

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()


        binding.takePhotoBtn.setOnClickListener {
            takePhoto()
        }

        binding.diagnoseBtn.setOnClickListener {
            predict()
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }


    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()

                    binding.imageCaptured.setImageURI(savedUri)

                }
            })
    }

    private fun predict() {
        val model = Model.newInstance(requireActivity())

        val `is`: InputStream? =
            savedUri?.let { requireActivity().contentResolver.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(`is`)
        `is`?.close()


        val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        val imageProcessor = ImageProcessor.Builder()
            .add(
                ResizeOp(
                    224,
                    224,
                    ResizeOp.ResizeMethod.BILINEAR
                )
            ) //.add(new NormalizeOp(127.5f, 127.5f))
            .build()

        // Create a TensorImage object. This creates the tensor of the corresponding
        // tensor type (float32 in this case) that the TensorFlow Lite interpreter needs.
        var tImage = TensorImage(DataType.FLOAT32)

        // Analyses and prepocess code for every frame
        tImage.load(resized)
        tImage = imageProcessor.process(tImage)

        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

        val byteBuffer = tImage.buffer

        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0).outputFeature0AsTensorBuffer.floatArray

        val max = getMax(outputs)

        binding.solutionText.text = labelList[max]

        model.close()
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )


            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }


    private fun getMax(outputs: FloatArray): Int {
        var ind = 0
        var min = 0.0f
        //0..3 cause i only have four probabilities, i.e healthy,blight, common rust,grey leaf spot
        for (i in 0..3) {
            if (outputs[i] > min) {
                ind = i
                min = outputs[i]
            }
        }
        Log.d("Value", outputs[ind].toString())
        return ind
    }

    override fun onStop() {
        super.onStop()
        cameraExecutor.shutdown()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }
}
