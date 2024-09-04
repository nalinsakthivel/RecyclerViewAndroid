package com.n.rv.view.product.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.n.rv.view.product.viewmodel.ProductViewModel
import com.n.rv.R
import com.n.rv.adapter.RecycleViewAdapter
import com.n.rv.databinding.FragmentProductListBinding
import com.n.rv.roomdatabase.AppDatabase
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel
import com.n.rv.utils.isInternetAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductList : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var viewModel: ProductViewModel
    private val tag: String = "ProductList"
    private lateinit var fragmentContext: Context
    private lateinit var database: AppDatabase
    private var recyclerViewState: Parcelable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
        database = AppDatabase.getInstance(context)
        Log.d("FragmentLifecycle", "onAttach called")
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
            backButton.visibility = View.INVISIBLE
        }
        initViewModel(this.fragmentContext)
        Log.d("FragmentLifecycle", "onCreateView called")
        return binding.root
    }

    private fun initViewModel(context: Context) {
        binding.customProgress.progressBar.visibility = View.VISIBLE
        val isNetwork = isInternetAvailable(context)
        if (isNetwork) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getData()
                withContext(Dispatchers.Main) {
                    viewModel.liveData.observe(viewLifecycleOwner) {
                        if (it !== null) onSetData(it)
                    }
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
        recycleViewAdapter = RecycleViewAdapter(getAPIDataClasses, mListener = {
            Log.d(tag, "onResponse: $it")
            recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
            val bundle = Bundle().apply {
                putSerializable("productData", it)
            }
            Handler(Looper.getMainLooper()).postDelayed(Runnable{
              findNavController().navigate(R.id.product_list_fragment_action, bundle)
            }, 300)
        })

        binding.recyclerView.apply {
            layoutManager?.onRestoreInstanceState(recyclerViewState)
            adapter = recycleViewAdapter
            setBackgroundColor(fragmentContext.getColor(R.color.white))
        }
        binding.customProgress.progressBar.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FragmentLifecycle", "onViewCreated called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentLifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentLifecycle", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentLifecycle", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentLifecycle", "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentLifecycle", "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentLifecycle", "onDestroy called")
    }

}