package com.example.productapp.product.api

import com.example.productapp.base.model.ResponseModel
import com.example.productapp.product.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

interface ProductApiInterface {
    @GET("/products")
    fun getProducts(): ResponseModel<List<Product>>

    @GET("/products/search")
    fun getProducts(@Query("q") query: String): ResponseModel<List<Product>>
}