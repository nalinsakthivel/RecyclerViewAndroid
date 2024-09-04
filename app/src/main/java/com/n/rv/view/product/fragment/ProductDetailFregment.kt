package com.n.rv.view.product.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.n.rv.R
import com.n.rv.databinding.FragmentProductDetailBinding
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel
import com.n.rv.utils.isInternetAvailable

class ProductDetail : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val logTag: String = "ProductDetail"
    private val intentRequestCode = 200
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openCamera()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showPermissionRationaleDialog()
            } else {
                showSettingsDialog()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val productData = arguments?.getSerializable("productData") as? ProductListDbModel

        binding.customHeader.apply {
            title.text = getString(R.string.product_detail)
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.apply {
            Glide.with(requireContext()).load(productData?.productImage)
                .placeholder(R.drawable.baseline_android_24)
                .error(R.drawable.baseline_android_24).into(productImage)

            productImage.setOnClickListener {
                Log.d(logTag, "onCreateView: Image Pressed")
                checkCameraPermission()
            }

            productName.text = productData?.productName
        }

        if (isInternetAvailable(requireContext())) {
            binding.productDescription.text = productData?.productDescription
        }

        return binding.root
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Camera Permission Required")
            .setMessage("This app requires camera access to take photos. Please grant the permission to continue using this feature.")
            .setPositiveButton("OK") { _, _ ->
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showSettingsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Required")
            .setMessage("Camera permission is required for this feature. Please enable it in the app settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun openCamera() {
        Log.d(logTag, "openCamera: Attempting to open camera")
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivityForResult(takePictureIntent, intentRequestCode)
            } else {
                Log.d(logTag, "openCamera: No camera app available")
            }
        } catch (e: ActivityNotFoundException) {
            Log.d(logTag, "openCamera: Camera app not found", e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == intentRequestCode) {
            Log.d(logTag, "onActivityResult: ${data?.extras}")
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                binding.productImage.setImageBitmap(imageBitmap)
                Log.d(logTag, "onActivityResult: Image set successfully")
            } else {
                Log.d(logTag, "onActivityResult: No image data found")
            }
        } else {
            Log.d(logTag, "onActivityResult: Failed to capture image")
        }
    }

}
