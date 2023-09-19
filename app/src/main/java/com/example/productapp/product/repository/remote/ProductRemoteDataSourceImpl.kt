package com.example.productapp.product.repository.remote

import javax.inject.Inject


class ProductRemoteDataSourceImpl @Inject constructor(
    private val productApiInterface: ProductApiInterface
) : ProductRemoteDataSource {

    override suspend fun getProducts(search: String?) = search?.let {
        productApiInterface.getProducts(it)
    } ?: run {
        productApiInterface.getProducts()
    }

    override suspend fun getProduct(id: Int) = productApiInterface.getProduct(id)
}