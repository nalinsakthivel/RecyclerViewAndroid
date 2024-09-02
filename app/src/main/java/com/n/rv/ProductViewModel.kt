package com.n.rv

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class ProductViewModel(application: Application) : AndroidViewModel(application), Serializable {
    private var _productsMutableData = MutableLiveData<List<ProductListDbModel>?>()
    val liveData: MutableLiveData<List<ProductListDbModel>?> get() = _productsMutableData
    private lateinit var apiService: ApiService
    private var tag: String = "ProductListViewModel"
    private var database: AppDatabase = AppDatabase.getInstance(application)

    fun getData() {
        viewModelScope.launch {
            try {

                apiService = ApiClient.getInstance().create(ApiService::class.java)
                val dataList = apiService.getProducts()
                dataList.enqueue(object : Callback<List<GetAPIDataClass>> {
                    override fun onResponse(
                        call: Call<List<GetAPIDataClass>>,
                        response: Response<List<GetAPIDataClass>>
                    ) {
                        if (response.isSuccessful) {
                            viewModelScope.launch(Dispatchers.IO) {
                                val productList = response.body()
                                    ?.let {
                                        this@ProductViewModel.database.listDao()
                                            .mapToProductListDbModel(it)
                                    }
                                if (productList != null) {
                                    this@ProductViewModel.database.listDao().insertAll(productList)
                                }
                                withContext(Dispatchers.Main) {
                                    _productsMutableData.value = productList
                                }
                            }
                        } else {
                            Log.d(tag, "onResponse: Error code ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<List<GetAPIDataClass>>, error: Throwable) {
                        Log.e(tag, "onFailure: ${error.message}")
                    }
                })
            } catch (e: Exception) {
                Log.e(tag, "Exception in getData: ${e.message}")
            }
        }
    }

}
