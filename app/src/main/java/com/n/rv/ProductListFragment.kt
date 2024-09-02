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
import com.n.rv.databinding.FragmentProductListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductList : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var viewModel: ProductViewModel
    private val tag: String = "ProductList"
    private var scrollPosition: Int = 0
    private lateinit var fragmentContext: Context
    private lateinit var database: AppDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
        database = AppDatabase.getInstance(fragmentContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[ProductViewModel::class.java]
        binding.customHeader.apply {
            title.text = getString(R.string.product)
            backButton.setOnClickListener {
            }
        }


        initViewModel(this.fragmentContext)
        return binding.root
    }

    private fun initViewModel(context: Context) {
        val isNetwork = isInternetAvailable(context)
        if (isNetwork) {
            viewModel.getData()
            viewModel.liveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    onSetData(it)
                }
            }
            Log.d(tag, "initViewModel: here 1 ")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val getAPIDataClasses = database.listDao().getAll()
                withContext(Dispatchers.Main) {
                    onSetData(getAPIDataClasses)
                }

                Log.d(tag, "initViewModel: here 2")
            }
        }

    }

    private fun onSetData(getAPIDataClasses: List<ProductListDbModel>) {
        binding.customProgress.progressBar.visibility = View.VISIBLE
        recycleViewAdapter = RecycleViewAdapter(getAPIDataClasses, mListener = {
            Log.d(tag, "onResponse: $it")
            val bundle = Bundle()
            bundle.putSerializable("productData", it)
            findNavController().navigate(R.id.product_list_fragment_action, bundle)
        })
        binding.recyclerView.apply {
            layoutManager?.scrollToPosition(scrollPosition)
            adapter = recycleViewAdapter
            setBackgroundColor(fragmentContext.getColor(R.color.white))
        }
        binding.customProgress.progressBar.visibility = View.GONE
    }


}