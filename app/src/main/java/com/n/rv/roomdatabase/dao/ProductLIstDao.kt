package com.n.rv.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.n.rv.roomdatabase.dbmodel.ProductListDbModel
import com.n.rv.model.GetAPIDataClass

@Dao
interface ProductLIstDao {
    @Query("SELECT * FROM productList ORDER by id")
    fun getAll(): List<ProductListDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(productListDbModel: List<ProductListDbModel>?)

    @Delete
    fun delete(productListDbModel: ProductListDbModel?)

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