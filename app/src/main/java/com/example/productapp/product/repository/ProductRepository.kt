package com.example.productapp.product.repository

import com.example.productapp.base.model.ResponseModel
import com.example.productapp.base.repository.BaseRepository
import com.example.productapp.product.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productRepositoryHelper: ProductRepositoryHelper
) : BaseRepository() {

    /**
     * Remote
     */
    suspend fun getProducts(search: String? = null): Flow<ResponseModel<List<Product>>> =
        productRepositoryHelper.getProducts(search)

    suspend fun getProduct(id: Int): Flow<Product> =
        productRepositoryHelper.getProduct(id)


    /**
     * Local
     */
    suspend fun getSavedProducts(): Flow<List<Product>> =
        productRepositoryHelper.getSavedProducts()

    suspend fun getSavedProduct(id: Int): Flow<Product> =
        productRepositoryHelper.getSavedProduct(id)

    suspend fun save(product: Product): Flow<Long> =
        productRepositoryHelper.save(product)

    suspend fun remove(product: Product): Flow<Int> =
        productRepositoryHelper.remove(product)
}
