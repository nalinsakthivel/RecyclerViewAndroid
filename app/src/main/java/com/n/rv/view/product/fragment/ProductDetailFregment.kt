package com.n.rv.view.product.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.n.rv.R
import com.n.rv.databinding.FragmentProductDetailBinding
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel
import com.n.rv.utils.isInternetAvailable

class ProductDetail : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val productData = arguments?.getSerializable("productData") as? ProductListDbModel
        val isNetwork = isInternetAvailable(requireContext())
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
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
            productName.text = productData?.productName
        }

        isNetwork.let {
            binding.productDescription.text = productData?.productDescription
        }

        return binding.root
    }
}