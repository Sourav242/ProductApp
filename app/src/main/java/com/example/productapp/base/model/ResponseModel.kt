package com.example.productapp.base.model


import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("products")
    val products: T,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)