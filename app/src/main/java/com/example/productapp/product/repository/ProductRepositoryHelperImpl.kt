package com.example.productapp.product.repository

import com.example.productapp.product.model.Product
import com.example.productapp.product.repository.local.ProductLocalDataSource
import com.example.productapp.product.repository.remote.ProductRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryHelperImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : ProductRepositoryHelper {

    /**
     * Remote
     */

    override suspend fun getProducts(search: String?) = flow {
        emit(
            search?.let {
                productRemoteDataSource.getProducts(it)
            } ?: run {
                productRemoteDataSource.getProducts()
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getProduct(id: Int) = flow {
        emit(
            productRemoteDataSource.getProduct(id)
        )
    }.flowOn(Dispatchers.IO)

    /**
     * Local
     */

    override suspend fun getSavedProducts() = flow {
        emit(
            productLocalDataSource.getSavedProducts()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getSavedProduct(id: Int) = flow {
        emit(
            productLocalDataSource.getSavedProduct(id)
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun save(product: Product) = flow {
        emit(
            productLocalDataSource.save(product)
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun remove(product: Product) = flow {
        emit(
            productLocalDataSource.remove(product)
        )
    }.flowOn(Dispatchers.IO)
}
