package com.n.rv

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.n.rv.databinding.FragmentProductListBinding


class ProductList : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var viewModel: ProductViewModel
    private val tag: String = "ProductList"
    private var scrollPosition: Int = 0
    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        binding.customHeader.apply {
            title.text = getString(R.string.product)
            backButton.setOnClickListener {
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) {
            if (it != null) {
                onSetData(it)
            }
        }
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        viewModel.getData()
    }

    private fun onSetData(products: List<GetAPIDataClass>) {
        binding.customProgress.progressBar.visibility = View.VISIBLE
        binding.recyclerView.layoutManager?.scrollToPosition(scrollPosition)
        recycleViewAdapter = RecycleViewAdapter(products, mListener = {
            scrollPosition =
                (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            Log.d(tag, "onResponse: $it")
            val bundle = Bundle()
            bundle.putSerializable("productData", it)
            findNavController().navigate(R.id.product_list_fragment_action, bundle)
        })
        binding.customProgress.progressBar.visibility = View.GONE
        binding.recyclerView.apply {
            setBackgroundColor(fragmentContext.getColor(R.color.white))
        }
        binding.recyclerView.adapter = recycleViewAdapter
    }

}