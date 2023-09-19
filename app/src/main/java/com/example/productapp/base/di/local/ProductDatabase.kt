package com.example.productapp.base.di.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.productapp.product.model.Converters
import com.example.productapp.product.model.Product
import com.example.productapp.product.repository.local.ProductDao
import com.example.productapp.product.utils.ProductConstants

@Database(entities = [Product::class], version = ProductConstants.DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}