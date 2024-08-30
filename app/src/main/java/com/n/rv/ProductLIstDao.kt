package com.n.rv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductLIstDao {
    @Query("SELECT * FROM ProductListDbModel ORDER by id")
    fun getAll(): List<ProductListDbModel>

    @Insert
    fun insertAll(vararg productListDbModel: ProductListDbModel)

    @Update
    fun updateById(id: Int, productListDbModel: ProductListDbModel)
}