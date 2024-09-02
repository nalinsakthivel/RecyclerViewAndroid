package com.n.rv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductLIstDao {
    @Query("SELECT * FROM ProductListDbModel ORDER by id")
    fun getAll(): List<ProductListDbModel>

    @Insert
    fun insertAll(productListDbModel: List<ProductListDbModel>?)

    fun mapToProductListDbModel(getAPIDataClass: List<GetAPIDataClass>): List<ProductListDbModel> {
        return getAPIDataClass.map {
            ProductListDbModel(
                it.title,
                it.description,
                it.image,
                it.price,
                it.category
            )

        }
    }
}