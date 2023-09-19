package com.example.productapp.product.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("brand")
    var brand: String = "",
    @SerializedName("category")
    var category: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("discountPercentage")
    var discountPercentage: Double = 0.0,
    @SerializedName("images")
    var images: List<String> = listOf(),
    @SerializedName("price")
    var price: Int = 0,
    @SerializedName("rating")
    var rating: Double = 0.0,
    @SerializedName("stock")
    var stock: Int = 0,
    @SerializedName("thumbnail")
    var thumbnail: String = "",
    @SerializedName("title")
    var title: String = ""
)