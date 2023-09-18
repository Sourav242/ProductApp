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

    init {
        getProducts()
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
}