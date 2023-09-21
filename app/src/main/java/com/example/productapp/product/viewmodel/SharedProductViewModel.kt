package com.example.productapp.product.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.productapp.ProductApplication
import com.example.productapp.base.model.NetworkState
import com.example.productapp.base.model.ResponseModel
import com.example.productapp.base.viewmodel.BaseViewModel
import com.example.productapp.product.model.Product
import com.example.productapp.product.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

@HiltViewModel
class SharedProductViewModel @Inject constructor(
    application: ProductApplication,
    private val repository: ProductRepository
) : BaseViewModel(application, repository) {

    private val productsStateFlow: MutableStateFlow<NetworkState<List<Product>>> =
        MutableStateFlow(NetworkState.Empty(listOf()))
    val _productsStateFlow: StateFlow<NetworkState<List<Product>>> = productsStateFlow

    private val productStateFlow: MutableStateFlow<NetworkState<Product>> =
        MutableStateFlow(NetworkState.Empty(Product()))
    val _productStateFlow: StateFlow<NetworkState<Product>> = productStateFlow

    private val savedProductsStateFlow: MutableStateFlow<NetworkState<List<Product>>> =
        MutableStateFlow(NetworkState.Empty(listOf()))
    val _savedProductsStateFlow: StateFlow<NetworkState<List<Product>>> = savedProductsStateFlow

    private val savedProductStateFlow: MutableStateFlow<NetworkState<Product>> =
        MutableStateFlow(NetworkState.Empty(Product()))
    val _savedProductStateFlow: StateFlow<NetworkState<Product>> = savedProductStateFlow

    init {
        getProducts()
        getSavedProducts()
    }

    fun getProducts(search: String? = null) = viewModelScope.launch {
        productsStateFlow.value.data?.let {
            productsStateFlow.value = NetworkState.LoadingWithData(it)
        } ?: run {
            productsStateFlow.value = NetworkState.Loading()
        }
        repository.getProducts(search)
            .catch { e ->
                productsStateFlow.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                productsStateFlow.value.data?.let {
                    ResponseModel(0, it, 0, 0)
                } ?: run {
                    ResponseModel(0, listOf(), 0, 0)
                }
            ).collect {
                val response = it.products
                productsStateFlow.value = NetworkState.Success(response)
                Log.d("apiResponse", response.toString())
            }
    }

    fun getProduct(id: Int) = viewModelScope.launch {
        productStateFlow.value.data?.let {
            productStateFlow.value = NetworkState.LoadingWithData(it)
        } ?: run {
            productStateFlow.value = NetworkState.Loading()
        }
        repository.getProduct(id)
            .catch { e ->
                productsStateFlow.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                productStateFlow.value.data ?: Product()
            ).collect {
                val response = it
                productStateFlow.value = NetworkState.Success(response)
                Log.d("apiResponse", response.toString())
            }
    }

    fun getSavedProducts(search: String? = null) = viewModelScope.launch {
        savedProductsStateFlow.value.data?.let {
            savedProductsStateFlow.value = NetworkState.LoadingWithData(it)
        } ?: run {
            savedProductsStateFlow.value = NetworkState.Loading()
        }
        repository.getSavedProducts(search)
            .catch { e ->
                savedProductsStateFlow.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                savedProductsStateFlow.value.data ?: listOf()
            ).collect {
                val response = it
                savedProductsStateFlow.value = NetworkState.Success(response)
                Log.d("queryResponse", response.toString())
            }
    }

    fun getSavedProduct(id: Int) = viewModelScope.launch {
        savedProductStateFlow.value.data?.let {
            savedProductStateFlow.value = NetworkState.LoadingWithData(it)
        } ?: run {
            savedProductStateFlow.value = NetworkState.Loading()
        }
        repository.getSavedProduct(id)
            .catch { e ->
                productsStateFlow.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                savedProductStateFlow.value.data ?: Product()
            ).collect {
                val response = it
                response.let {
                    savedProductStateFlow.value = NetworkState.Success(response)
                    Log.d("queryResponse", response.toString())
                }
            }
    }

    private val savedState: MutableStateFlow<NetworkState<Long>> =
        MutableStateFlow(NetworkState.Empty(0))
    val _savedState = savedState

    fun save(product: Product) = viewModelScope.launch {
        repository.save(product)
            .catch { e ->
                savedState.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                0L
            ).collect {
                val response = it
                if (response > 0) {
                    savedState.value = NetworkState.Success(response)
                    Log.d("queryResponse", response.toString())
                }
            }
    }

    fun remove(product: Product) = viewModelScope.launch {
        repository.remove(product)
            .catch { e ->
                savedState.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                0
            ).collect {
                val response = it
                if (response > 0) {
                    savedState.value = NetworkState.Success(response.toLong())
                    Log.d("queryResponse", response.toString())
                }
            }
    }
}