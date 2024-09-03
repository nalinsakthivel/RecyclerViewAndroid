package com.n.rv.network

import com.n.rv.model.GetAPIDataClass
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<GetAPIDataClass>>
}