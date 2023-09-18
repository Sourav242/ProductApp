package com.example.productapp.product.repository

import com.example.productapp.base.model.ResponseModel
import com.example.productapp.base.repository.BaseRepository
import com.example.productapp.product.api.ProductApiInterface
import com.example.productapp.product.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor() : BaseRepository() {
    @Inject
    lateinit var productApiInterface: ProductApiInterface

    suspend fun getProducts(search: String? = null): Flow<ResponseModel<List<Product>>> = flow {
        emit(
            search?.let {
                productApiInterface.getProducts(it)
            }?: run {
                productApiInterface.getProducts()
            }
        )
    }.flowOn(Dispatchers.IO)
}
