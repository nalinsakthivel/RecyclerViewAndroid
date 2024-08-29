package com.n.rv

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private var _productsMutableData = MutableLiveData<List<GetAPIDataClass>?>()
    val liveData: MutableLiveData<List<GetAPIDataClass>?> get() = _productsMutableData
    private lateinit var apiService: ApiService
    private val tag: String = "ProductListViewModel"

    fun getData() {
        try {
            apiService = ApiClient.getInstance().create(ApiService::class.java)
            val dataList = apiService.getProducts()
            dataList.enqueue(object : Callback<List<GetAPIDataClass>> {
                override fun onResponse(
                    call: Call<List<GetAPIDataClass>>,
                    response: Response<List<GetAPIDataClass>>
                ) {
                    if (response.isSuccessful) {
                      _productsMutableData.value = response.body()

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
