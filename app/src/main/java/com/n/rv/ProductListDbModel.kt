package com.n.rv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductListDbModel(
    @ColumnInfo(name = "Product Name")
    val productName : String,
    @ColumnInfo(name = "Product Description")
    val productDescription : String,
    @ColumnInfo(name = "Product Image")
    val productImage : String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
