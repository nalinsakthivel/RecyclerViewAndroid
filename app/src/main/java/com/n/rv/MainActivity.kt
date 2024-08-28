package com.n.rv

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.n.rv.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val tag: String = "MyActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.customHeader.apply {
            title.text = getString(R.string.product)
            backButton.setOnClickListener {

            }
        }
        init()
        getData()
    }


    private fun init() {
        apiService = ApiClient.getInstance().create(ApiService::class.java)
    }

    private fun getData() {
        try {
            binding.customProgress.progressBar.visibility=View.VISIBLE
            val dataList = apiService.getProducts()
            dataList.enqueue(object : Callback<List<GetAPIDataClass>> {
                override fun onResponse(
                    call: Call<List<GetAPIDataClass>>,
                    response: Response<List<GetAPIDataClass>>
                ) {
                    if (response.isSuccessful) {
                        val products = response.body()
                        if (!products.isNullOrEmpty()) {
                            Log.d(tag, "onResponse: $products")
                            Handler(mainLooper).postDelayed({
                                recycleViewAdapter = RecycleViewAdapter(products, mListener = {
                                    Log.d(tag, "onResponse: $it")
                                })
                                binding.customProgress.progressBar.visibility=View.GONE
                                binding.recyclerView.apply {
                                    setBackgroundColor(getColor(R.color.white))
                                }
                                binding.recyclerView.adapter = recycleViewAdapter
                            },2000)
                        }
                    } else {
                        Log.d(tag, "onResponse: Error code ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<GetAPIDataClass>>, error: Throwable) {
                    throw error
                }
            })
        } catch (e: Exception) {
            Log.e(tag, "Exception in getData: ${e.message}")
        }
    }

}