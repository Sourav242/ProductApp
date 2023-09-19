package com.example.productapp.product.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.productapp.product.model.Product

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getSavedProducts(): List<Product>

    @Query("SELECT * FROM product WHERE category LIKE '%' || (:category) || '%'")
    fun getSavedProducts(category: String): List<Product>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getSavedProduct(id: Int): Product

    @Insert
    fun save(product: Product): Long

    @Delete
    fun remove(product: Product): Int
}