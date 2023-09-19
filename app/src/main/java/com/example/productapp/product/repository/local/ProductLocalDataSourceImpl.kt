package com.example.productapp.product.repository.local

import com.example.productapp.product.model.Product
import javax.inject.Inject

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */
class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDataSource {

    override suspend fun getSavedProducts(category: String?): List<Product> {
        return category?.let {
            productDao.getSavedProducts(it)
        } ?: run {
            productDao.getSavedProducts()
        }
    }

    override suspend fun getSavedProduct(id: Int) = productDao.getSavedProduct(id)

    override suspend fun save(product: Product) = productDao.save(product)

    override suspend fun remove(product: Product) = productDao.remove(product)
}