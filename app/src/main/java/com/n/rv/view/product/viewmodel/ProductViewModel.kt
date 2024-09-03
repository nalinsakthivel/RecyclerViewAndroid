package com.n.rv.view.product.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.n.rv.network.ApiClient
import com.n.rv.network.ApiService
import com.n.rv.roomdatabase.AppDatabase
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class ProductViewModel(application: Application) : AndroidViewModel(application), Serializable {
    private var _productsMutableData = MutableLiveData<List<ProductListDbModel>?>()
    val liveData: MutableLiveData<List<ProductListDbModel>?> get() = _productsMutableData
    private lateinit var apiService: ApiService
    private var tag: String = "ProductListViewModel"
    private var database: AppDatabase = AppDatabase.getInstance(application)

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiService = ApiClient.getInstance().create(ApiService::class.java)
                val response = apiService.getProducts()
                if (response.isSuccessful && response.body() !== null) {
                    Log.d(tag, "Response: ${response.body()}")
                    val productList = response.body()?.let {
                        database.listDao().mapToProductListDbModel(it)
                    }
                    if (productList != null) {
                        database.listDao().insertAll(productList)
                    }
                    withContext(Dispatchers.Main) {
                        _productsMutableData.value = productList
                    }
                } else {
                    Log.d(tag, "Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Exception in getData: ${e.message}")
            }
        }
    }


}
