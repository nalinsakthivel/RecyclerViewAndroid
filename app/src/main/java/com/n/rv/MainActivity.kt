package com.n.rv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        init()
        getData()
    }


    private fun init() {
        apiService = ApiClient.getInstance().create(ApiService::class.java)
    }

    private fun getData() {

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
                        recycleViewAdapter = RecycleViewAdapter(products, mListener = {
                            Log.d(tag, "onResponse: $it")
                        })
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.recyclerView.adapter = recycleViewAdapter
                    }

                } else {
                    Log.d(tag, "onResponse: Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GetAPIDataClass>>, error: Throwable) {
                Log.d(tag, "onFailure: ${error.message}")
            }

        })
    }
}