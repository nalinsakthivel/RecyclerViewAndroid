package com.n.rv.roomdatabase.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index(value = ["Product Name"], unique = true,)], tableName = "productList")
data class ProductListDbModel(
    @ColumnInfo(name = "Product Name")
    val productName: String,
    @ColumnInfo(name = "Product Description")
    val productDescription: String,
    @ColumnInfo(name = "Product Image")
    val productImage: String,
    @ColumnInfo(name = "Product Price")
    val productPrice: Double,
    @ColumnInfo(name = "Product Category")
    val productCategory: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

//@Entity
//data class RatingDbModel(
//    @ColumnInfo(name = "Rate")
//    val rate : Double,
//    @ColumnInfo(name = "Count")
//    val count : Int,
//){
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//}